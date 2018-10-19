package com.sd.lib.eos.rpc.core;

import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.output.SignedTransaction;
import com.sd.lib.eos.rpc.output.TransactionQuery;

public interface TransactionSigner
{
    SignedTransaction signTransaction(TransactionQuery query, GetInfoResponse infoResponse, GetBlockResponse blockResponse, String privateKey);
}
