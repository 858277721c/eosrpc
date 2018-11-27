package com.sd.lib.eos.rpc.core.output;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.TransactionSigner;
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
public class PushTransaction
{
    private final RpcApi mRpcApi = new RpcApi();
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

    /**
     * 提交交易(同步执行)
     *
     * @param privateKey
     * @param callback
     * @throws RpcException
     */
    public final void submit(String privateKey, Callback callback) throws RpcException
    {
        Utils.checkEmpty(privateKey, "private key is empty");
        Utils.checkNotNull(callback, "callback is null");

        final String publicKey = FEOSManager.getInstance().getEccTool().privateToPublicKey(privateKey);
        Utils.checkEmpty(publicKey, "private key format error");

        if (!checkAuthorizationPermission(publicKey, callback))
            return;

        final List<ActionModel> listAction = new ArrayList<>();
        for (ActionParams item : mActionParams)
        {
            final AuthorizationModel authorizationModel = item.getAuthorization();
            authorizationModel.check();

            final String code = item.getCode();
            final String action = item.getAction();
            final ActionParams.Args args = item.getArgs();

            final ApiResponse<AbiJsonToBinResponse> apiResponse = mRpcApi.abiJsonToBin(code, action, args);
            if (!apiResponse.isSuccessful())
            {
                callback.onErrorApi(ApiError.AbiJsonToBin, apiResponse.getError(), null);
                return;
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

        final ApiResponse<GetInfoResponse> infoApiResponse = mRpcApi.getInfo();
        if (!infoApiResponse.isSuccessful())
        {
            callback.onErrorApi(ApiError.GetInfo, infoApiResponse.getError(), null);
            return;
        }

        final GetInfoResponse info = infoApiResponse.getSuccess();
        final String blockId = infoApiResponse.getSuccess().getHead_block_id();

        final ApiResponse<GetBlockResponse> blockApiResonse = mRpcApi.getBlock(blockId);
        if (!blockApiResonse.isSuccessful())
        {
            callback.onErrorApi(ApiError.GetBlock, blockApiResonse.getError(), null);
            return;
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
            signResult = getTransactionSigner().signTransaction(transaction, info.getChain_id(), privateKey);
        } catch (Exception e)
        {
            throw new RpcTransactionSignException("sign transaction error", e);
        }

        final ApiResponse<PushTransactionResponse> pushApiResponse = mRpcApi.pushTransaction(
                signResult.getSignatures(),
                signResult.getCompression(),
                null,
                signResult.getPacked_trx()
        );

        if (!pushApiResponse.isSuccessful())
        {
            callback.onErrorApi(ApiError.PushTransaction, pushApiResponse.getError(), null);
            return;
        }

        callback.onSuccess(pushApiResponse);
    }

    private boolean checkAuthorizationPermission(String publicKey, Callback callback) throws RpcException
    {
        if (!mCheckAuthorizationPermission)
            return true;

        final Map<String, String> mapPermission = new HashMap<>();
        for (ActionParams item : mActionParams)
        {
            final AuthorizationModel authorizationModel = item.getAuthorization();
            if (!Utils.isEmpty(authorizationModel.getPermission()))
                continue;

            final String actor = authorizationModel.getActor();
            Utils.checkEmpty(actor, "authorization actor was not specified");

            String savedPermission = mapPermission.get(actor);
            if (!Utils.isEmpty(savedPermission))
            {
                item.setAuthorizationPermission(savedPermission);
            } else
            {
                final ApiResponse<GetAccountResponse> apiResponse = mRpcApi.getAccount(actor);
                if (!apiResponse.isSuccessful())
                {
                    callback.onErrorApi(ApiError.GetAccount, apiResponse.getError(), null);
                    return false;
                }

                final GetAccountResponse response = apiResponse.getSuccess();

                GetAccountResponse.Permission targetPermission = null;
                for (GetAccountResponse.Permission itemPermission : response.getPermissions())
                {
                    boolean found = false;
                    for (GetAccountResponse.Permission.RequiredAuth.Key itemKeys : itemPermission.getRequired_auth().getKeys())
                    {
                        if (publicKey.equals(itemKeys.getKey()))
                        {
                            found = true;
                            break;
                        }
                    }

                    if (found)
                    {
                        targetPermission = itemPermission;
                        break;
                    }
                }

                if (targetPermission == null)
                {
                    callback.onError(Error.NotAccountKey, "The key provided is not the key of the account");
                    return false;
                }

                savedPermission = targetPermission.getPerm_name();
                mapPermission.put(actor, savedPermission);
                item.setAuthorizationPermission(savedPermission);
            }
        }

        return true;
    }

    protected TransactionSigner getTransactionSigner()
    {
        return FEOSManager.getInstance().getTransactionSigner();
    }

    public static abstract class Callback
    {
        public abstract void onSuccess(ApiResponse<PushTransactionResponse> response);

        public void onErrorApi(ApiError error, ErrorResponse errorResponse, String msg)
        {
            if (Utils.isEmpty(msg))
                msg = errorResponse.getCode() + " " + errorResponse.getMessage();
        }

        public void onError(Error error, String msg)
        {
        }
    }

    public enum Error
    {
        /**
         * 提供的密钥不是该账号对应的密钥
         */
        NotAccountKey,
    }

    public enum ApiError
    {
        GetAccount,
        AbiJsonToBin,
        GetInfo,
        GetBlock,
        PushTransaction,
    }
}
