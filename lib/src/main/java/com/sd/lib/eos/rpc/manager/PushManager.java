package com.sd.lib.eos.rpc.manager;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.output.TransactionQuery;
import com.sd.lib.eos.rpc.output.model.ActionModel;
import com.sd.lib.eos.rpc.output.model.PushTransactionModel;
import com.sd.lib.eos.rpc.output.model.TransactionModel;
import com.sd.lib.eos.rpc.params.ActionParams;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PushManager
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

    public void execute() throws Exception
    {
        final List<ActionParams> listParam = Collections.unmodifiableList(mListParam);
        if (listParam.isEmpty())
            throw new RuntimeException("empty action");

        final List<ActionModel> listAction = new ArrayList<>();
        for (ActionParams item : listParam)
        {
            final String code = item.getCode();
            final String action = item.getAction();
            final String argsJson = item.getArgs().toJson();

            final AbiJsonToBinResponse response = mRpcApi.abiJsonToBin(code, action, argsJson);
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

        final String sign = signTransaction(transaction);
        final String pack = packTransaction(transaction);

        final PushTransactionModel model = new PushTransactionModel();
        model.addSignature(sign);
        model.setPacked_trx(pack);

        final List<String> signatures = new ArrayList<>(1);
        signatures.add(sign);

        mRpcApi.pushTransaction();
    }

    protected abstract String signTransaction(TransactionQuery query);

    protected abstract String packTransaction(TransactionQuery query);
}
