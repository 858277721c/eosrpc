package com.sd.lib.eos.rpc.core;

import com.sd.lib.eos.rpc.output.PackedTransaction;
import com.sd.lib.eos.rpc.output.TransactionQuery;

public interface TransactionPacker
{
    PackedTransaction packTransaction(TransactionQuery query);
}
