package com.sd.lib.eos.rpc.output;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.TransactionSigner;
import com.sd.lib.eos.rpc.output.model.ActionModel;
import com.sd.lib.eos.rpc.output.model.TransactionModel;
import com.sd.lib.eos.rpc.output.model.TransactionSignResult;
import com.sd.lib.eos.rpc.params.ActionParams;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 提交交易
 */
public class PushTransaction
{
    private final RpcApi mRpcApi = new RpcApi();
    private final List<ActionParams> mListParam = new ArrayList<>();

    public void addAction(ActionParams model)
    {
        if (mListParam.contains(model))
            return;

        Utils.checkNotNull(model, "");
        mListParam.add(model);
    }

    /**
     * 提交交易
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public PushTransactionResponse submit(String privateKey) throws Exception
    {
        Utils.checkEmpty(privateKey, "");
        final List<ActionParams> listParam = Collections.unmodifiableList(mListParam);
        if (listParam.isEmpty())
            throw new RuntimeException("empty action");

        final List<ActionModel> listAction = new ArrayList<>();
        for (ActionParams item : listParam)
        {
            final String code = item.getCode();
            final String action = item.getAction();
            final ActionParams.Args args = item.getArgs();

            final AbiJsonToBinResponse response = mRpcApi.abiJsonToBin(code, action, args);
            final String binary = response.getBinargs();

            final ActionModel actionModel = new ActionModel();
            actionModel.setAccount(code);
            actionModel.setName(action);
            actionModel.setAuthorization(item.getAuthorization());
            actionModel.setData(binary);

            listAction.add(actionModel);
        }

        final GetInfoResponse info = mRpcApi.getInfo();
        final String blockId = info.getHead_block_id();
        final GetBlockResponse block = mRpcApi.getBlock(blockId);

        final TransactionModel transaction = new TransactionModel();
        transaction.setExpiration(info.getHeadBlockTimeAfter(30 * 1000));
        transaction.setRef_block_num(block.getBlock_num());
        transaction.setRef_block_prefix(block.getRef_block_prefix());
        transaction.setActions(listAction);

        final TransactionSignResult signedTransaction = getTransactionSigner().signTransaction(transaction, info, block, privateKey);

        return mRpcApi.pushTransaction(signedTransaction.getSignatures(),
                signedTransaction.getCompression(),
                null,
                signedTransaction.getPacked_trx());
    }

    protected TransactionSigner getTransactionSigner()
    {
        return FEOSManager.getInstance().getTransactionSigner();
    }
}
