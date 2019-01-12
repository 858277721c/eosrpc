package com.sd.lib.eos.rpc.core.output;

import com.sd.lib.eos.rpc.api.ApiType;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.output.model.ActionModel;
import com.sd.lib.eos.rpc.core.output.model.AuthorizationModel;
import com.sd.lib.eos.rpc.core.output.model.TransactionModel;
import com.sd.lib.eos.rpc.core.output.model.TransactionSignResult;
import com.sd.lib.eos.rpc.exception.RpcException;
import com.sd.lib.eos.rpc.exception.RpcTransactionSignException;
import com.sd.lib.eos.rpc.params.ActionParams;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提交交易
 */
public abstract class PushTransaction
{
    private RpcApi mRpcApi;
    private final ActionParams[] mActionParams;
    private final boolean mCheckAuthorizationPermission;

    public PushTransaction(ActionParams... params)
    {
        this(true, params);
    }

    public PushTransaction(boolean checkAuthorizationPermission, ActionParams... params)
    {
        if (params == null || params.length <= 0)
            throw new IllegalArgumentException("params was not specified");

        mActionParams = Arrays.copyOf(params, params.length);
        mCheckAuthorizationPermission = checkAuthorizationPermission;
    }

    public RpcApi getRpcApi()
    {
        if (mRpcApi == null)
            mRpcApi = new RpcApi();
        return mRpcApi;
    }

    /**
     * 提交交易(同步执行)
     *
     * @param privateKey
     * @return
     * @throws RpcException
     */
    public final boolean submit(String privateKey) throws RpcException
    {
        Utils.checkEmpty(privateKey, "private key is empty");

        final String publicKey = FEOSManager.getInstance().getEccTool().privateToPublicKey(privateKey);
        Utils.checkEmpty(publicKey, "private key format error");

        if (!checkAuthorizationPermission(publicKey))
            return false;

        final List<ActionModel> listAction = new ArrayList<>();
        for (ActionParams item : mActionParams)
        {
            final AuthorizationModel authorizationModel = item.getAuthorization();
            authorizationModel.check();

            final String code = item.getCode();
            final String action = item.getAction();
            final ActionParams.Args args = item.getArgs();

            final ApiResponse<AbiJsonToBinResponse> apiResponse = getRpcApi().abiJsonToBin(code, action, args);
            if (!apiResponse.isSuccessful())
            {
                onErrorApi(ApiType.AbiJsonToBin, apiResponse.getError());
                return false;
            }

            final AbiJsonToBinResponse response = apiResponse.getSuccess();
            final String binary = response.getBinargs();
            Utils.checkEmpty(binary, "abiJsonToBin failed with empty binary:" + code + " " + action);

            final ActionModel actionModel = new ActionModel();
            actionModel.setAccount(code);
            actionModel.setName(action);
            actionModel.setAuthorization(Collections.unmodifiableList(Arrays.asList(authorizationModel)));
            actionModel.setData(binary);

            listAction.add(actionModel);
        }

        final ApiResponse<GetInfoResponse> infoApiResponse = getRpcApi().getInfo();
        if (!infoApiResponse.isSuccessful())
        {
            onErrorApi(ApiType.GetInfo, infoApiResponse.getError());
            return false;
        }

        final GetInfoResponse info = infoApiResponse.getSuccess();
        final String blockId = infoApiResponse.getSuccess().getHead_block_id();

        final ApiResponse<GetBlockResponse> blockApiResonse = getRpcApi().getBlock(blockId);
        if (!blockApiResonse.isSuccessful())
        {
            onErrorApi(ApiType.GetBlock, blockApiResonse.getError());
            return false;
        }

        final GetBlockResponse block = blockApiResonse.getSuccess();

        final TransactionModel transaction = new TransactionModel();
        transaction.setExpiration(RpcUtils.addMilliSecond(info.getHead_block_time(), 60 * 1000));
        transaction.setRef_block_num(block.getBlock_num());
        transaction.setRef_block_prefix(block.getRef_block_prefix());
        transaction.setActions(listAction);

        TransactionSignResult signResult = null;
        try
        {
            signResult = FEOSManager.getInstance().getTransactionSigner().signTransaction(transaction, info.getChain_id(), privateKey);
        } catch (Exception e)
        {
            throw new RpcTransactionSignException("sign transaction error", e);
        }

        final ApiResponse<PushTransactionResponse> pushApiResponse = getRpcApi().pushTransaction(
                signResult.getSignatures(),
                signResult.getPacked_trx(),
                null,
                signResult.getCompression()
        );

        if (!pushApiResponse.isSuccessful())
        {
            onErrorApi(ApiType.PushTransaction, pushApiResponse.getError());
            return false;
        }

        onSuccess(pushApiResponse);
        return true;
    }

    private boolean checkAuthorizationPermission(String publicKey) throws RpcException
    {
        if (!mCheckAuthorizationPermission)
            return true;

        final Map<String, String> mapPermissionCache = new HashMap<>();
        for (ActionParams item : mActionParams)
        {
            final AuthorizationModel authorizationModel = item.getAuthorization();
            if (!Utils.isEmpty(authorizationModel.getPermission()))
                continue;

            final String actor = authorizationModel.getActor();
            Utils.checkEmpty(actor, "authorization actor was not specified");

            final String savedPermission = mapPermissionCache.get(actor);
            if (!Utils.isEmpty(savedPermission))
            {
                item.setAuthorizationPermission(savedPermission);
            } else
            {
                final ApiResponse<GetAccountResponse> apiResponse = getRpcApi().getAccount(actor);
                if (!apiResponse.isSuccessful())
                {
                    onErrorApi(ApiType.GetAccount, apiResponse.getError());
                    return false;
                }

                final GetAccountResponse response = apiResponse.getSuccess();
                final Map<String, GetAccountResponse.Permission> permissions = response.getPermission(publicKey);

                if (permissions == null || permissions.isEmpty())
                {
                    onError(Error.NotAccountKey, "The key provided is not the key of the account");
                    return false;
                }

                final List<GetAccountResponse.Permission> listPermission = new ArrayList<>(permissions.values());
                final String permissionName = listPermission.get(0).getPerm_name();

                mapPermissionCache.put(actor, permissionName);
                item.setAuthorizationPermission(permissionName);
            }
        }

        return true;
    }

    protected abstract void onSuccess(ApiResponse<PushTransactionResponse> response);

    protected abstract void onErrorApi(ApiType apiType, ErrorResponse errorResponse);

    protected abstract void onError(Error error, String msg);

    public enum Error
    {
        /**
         * 提供的密钥不是该账号对应的密钥
         */
        NotAccountKey,
    }
}
