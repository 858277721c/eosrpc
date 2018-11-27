package com.sd.lib.eos.rpc.core.output;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
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
                callback.onErrorAbiJsonToBin(apiResponse, "abiJsonToBin failed");
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
            callback.onErrorGetInfo(infoApiResponse, "getInfo fialed");
            return;
        }

        final GetInfoResponse info = infoApiResponse.getSuccess();
        final String blockId = infoApiResponse.getSuccess().getHead_block_id();

        final ApiResponse<GetBlockResponse> blockApiResonse = mRpcApi.getBlock(blockId);
        if (!blockApiResonse.isSuccessful())
        {
            callback.onErrorGetBlock(blockApiResonse, "getBlock failed");
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
            callback.onErrorPushTransaction(pushApiResponse, "push transaction failed");
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

            final String savedPermission = mapPermission.get(actor);
            if (!Utils.isEmpty(savedPermission))
            {
                item.setAuthorizationPermission(savedPermission);
            } else
            {
                final ApiResponse<GetAccountResponse> apiResponse = mRpcApi.getAccount(actor);
                if (!apiResponse.isSuccessful())
                {
                    callback.onErrorGetAccount(apiResponse, "getAccount failed");
                    return false;
                }

                final GetAccountResponse response = apiResponse.getSuccess();
                final List<GetAccountResponse.Permission> permissions = response.getPermissions();
                if (permissions == null || permissions.isEmpty())
                {
                    callback.onError("getAccount permissions is empty");
                    return false;
                }

                GetAccountResponse.Permission targetPermission = null;
                for (GetAccountResponse.Permission itemPermission : permissions)
                {
                    final List<GetAccountResponse.Permission.RequiredAuth.Key> keys = itemPermission.getRequired_auth().getKeys();
                    if (keys == null || keys.isEmpty())
                    {
                        callback.onError("getAccount permissions keys is empty");
                        return false;
                    }

                    boolean found = false;
                    for (GetAccountResponse.Permission.RequiredAuth.Key itemKeys : keys)
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
                    callback.onError("public key was not found in getAccount permissions");
                    return false;
                }

                mapPermission.put(actor, targetPermission.getPerm_name());
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

        public void onErrorGetAccount(ApiResponse<GetAccountResponse> response, String msg)
        {
        }

        public void onErrorAbiJsonToBin(ApiResponse<AbiJsonToBinResponse> response, String msg)
        {
        }

        public void onErrorGetInfo(ApiResponse<GetInfoResponse> response, String msg)
        {
        }

        public void onErrorGetBlock(ApiResponse<GetBlockResponse> response, String msg)
        {
        }

        public void onErrorPushTransaction(ApiResponse<PushTransactionResponse> response, String msg)
        {
        }

        public void onError(String msg)
        {
        }
    }
}
