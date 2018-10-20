package com.sd.lib.eos.rpc.core;

import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.output.model.TransactionQuery;
import com.sd.lib.eos.rpc.output.model.TransactionSignResult;

public interface TransactionSigner
{
    TransactionSignResult signTransaction(TransactionQuery query, GetInfoResponse infoResponse, GetBlockResponse blockResponse, String privateKey);
}
