package com.sd.lib.eos.rpc.core.output;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.TransactionSigner;
import com.sd.lib.eos.rpc.core.output.model.ActionModel;
import com.sd.lib.eos.rpc.core.output.model.TransactionModel;
import com.sd.lib.eos.rpc.core.output.model.TransactionSignResult;
import com.sd.lib.eos.rpc.exception.RpcException;
import com.sd.lib.eos.rpc.exception.RpcTransactionSignException;
import com.sd.lib.eos.rpc.params.ActionParams;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 提交交易
 */
public class PushTransaction
{
    private final RpcApi mRpcApi = new RpcApi();
    private final ActionParams[] mActionParams;

    public PushTransaction(ActionParams... params)
    {
        if (params == null || params.length <= 0)
            throw new IllegalArgumentException("params was not specified");

        mActionParams = Arrays.copyOf(params, params.length);
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

        final List<ActionModel> listAction = new ArrayList<>();
        for (ActionParams item : mActionParams)
        {
            final String code = item.getCode();
            final String action = item.getAction();
            final ActionParams.Args args = item.getArgs();

            final ApiResponse<AbiJsonToBinResponse> apiResponse = mRpcApi.abiJsonToBin(code, action, args);
            if (!apiResponse.isSuccessful())
            {
                callback.onErrorAbiJsonToBin(apiResponse, "abiJsonToBin failed:" + code + " " + action);
                return;
            }

            final AbiJsonToBinResponse response = apiResponse.getSuccess();
            final String binary = response.getBinargs();
            Utils.checkEmpty(binary, "abiJsonToBin failed with empty binary:" + code + " " + action);

            final ActionModel actionModel = new ActionModel();
            actionModel.setAccount(code);
            actionModel.setName(action);
            actionModel.setAuthorization(item.getAuthorization());
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

    protected TransactionSigner getTransactionSigner()
    {
        return FEOSManager.getInstance().getTransactionSigner();
    }

    public interface Callback
    {
        void onSuccess(ApiResponse<PushTransactionResponse> response);

        void onErrorAbiJsonToBin(ApiResponse<AbiJsonToBinResponse> response, String msg);

        void onErrorGetInfo(ApiResponse<GetInfoResponse> response, String msg);

        void onErrorGetBlock(ApiResponse<GetBlockResponse> response, String msg);

        void onErrorPushTransaction(ApiResponse<PushTransactionResponse> response, String msg);
    }
}
