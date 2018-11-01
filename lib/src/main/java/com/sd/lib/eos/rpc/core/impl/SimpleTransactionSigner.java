package com.sd.lib.eos.rpc.core.impl;

import com.sd.lib.eos.rpc.core.TransactionSigner;
import com.sd.lib.eos.rpc.core.output.model.ActionQuery;
import com.sd.lib.eos.rpc.core.output.model.AuthorizationQuery;
import com.sd.lib.eos.rpc.core.output.model.TransactionQuery;
import com.sd.lib.eos.rpc.core.output.model.TransactionSignResult;
import com.sd.lib.eos.rpc.helper.chain.Action;
import com.sd.lib.eos.rpc.helper.chain.PackedTransaction;
import com.sd.lib.eos.rpc.helper.chain.SignedTransaction;
import com.sd.lib.eos.rpc.helper.cypto.ec.EosPrivateKey;
import com.sd.lib.eos.rpc.helper.types.TypeChainId;
import com.sd.lib.eos.rpc.helper.types.TypePermissionLevel;

import java.util.ArrayList;
import java.util.List;

public class SimpleTransactionSigner implements TransactionSigner
{
    @Override
    public TransactionSignResult signTransaction(TransactionQuery query, String chainId, String privateKey) throws Exception
    {
        final SignedTransaction transaction = new SignedTransaction();
        transaction.setExpiration(query.queryExpiration());
        transaction.setRef_block_num((int) query.queryRef_block_num());
        transaction.setRef_block_prefix(query.queryRef_block_prefix());

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

        transaction.sign(new EosPrivateKey(privateKey), new TypeChainId(chainId));
        final PackedTransaction packedTransaction = new PackedTransaction(transaction);

        final TransactionSignResult result = new TransactionSignResult(
                packedTransaction.signatures,
                packedTransaction.compression,
                packedTransaction.packed_trx
        );

        return result;
    }
}
