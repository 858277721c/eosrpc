package com.sd.eos.rpc;

import com.sd.eos.rpc.help.chain.Action;
import com.sd.eos.rpc.help.chain.PackedTransaction;
import com.sd.eos.rpc.help.chain.SignedTransaction;
import com.sd.eos.rpc.help.cypto.ec.EosPrivateKey;
import com.sd.eos.rpc.help.types.TypeChainId;
import com.sd.eos.rpc.help.types.TypePermissionLevel;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.core.TransactionSigner;
import com.sd.lib.eos.rpc.output.ActionQuery;
import com.sd.lib.eos.rpc.output.AuthorizationQuery;
import com.sd.lib.eos.rpc.output.TransactionQuery;

import java.util.ArrayList;
import java.util.List;

public class AppTransactionSigner implements TransactionSigner
{
    @Override
    public com.sd.lib.eos.rpc.output.SignedTransaction signTransaction(TransactionQuery query,
                                                                       GetInfoResponse infoResponse, GetBlockResponse blockResponse,
                                                                       String privateKey)
    {
        SignedTransaction transaction = new SignedTransaction();
        transaction.setExpiration(query.queryExpiration());
        transaction.setReferenceBlock(infoResponse.getHead_block_id());

        for (ActionQuery itemAction : query.queryActions())
        {
            final Action action = new Action();
            action.setAccount(itemAction.queryAccount());
            action.setName(itemAction.queryName());
            action.setData(itemAction.queryData());

            final List<TypePermissionLevel> listTypePermissionLevel = new ArrayList<>(1);
            for (AuthorizationQuery itemAuthor : itemAction.queryAuthorization())
            {
                final TypePermissionLevel typePermissionLevel = new TypePermissionLevel(itemAuthor.queryActor(), itemAuthor.queryPermission());
                listTypePermissionLevel.add(typePermissionLevel);
            }
            action.setAuthorization(listTypePermissionLevel);

            transaction.addAction(action);
        }

        transaction.sign(new EosPrivateKey(privateKey), new TypeChainId(infoResponse.getChain_id()));

        final PackedTransaction packedTransaction = new PackedTransaction(transaction);
        final com.sd.lib.eos.rpc.output.SignedTransaction result = new com.sd.lib.eos.rpc.output.SignedTransaction(
                packedTransaction.signatures,
                packedTransaction.compression,
                packedTransaction.packed_trx
        );

        return result;
    }
}
