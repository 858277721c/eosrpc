package com.sd.lib.eos.rpc.core;

import com.sd.lib.eos.rpc.output.TransactionQuery;

public interface TransactionSigner
{
    String signTransaction(TransactionQuery query);
}
