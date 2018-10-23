package com.sd.lib.eos.rpc.core;

import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.core.output.model.TransactionQuery;
import com.sd.lib.eos.rpc.core.output.model.TransactionSignResult;

/**
 * 交易签名接口
 */
public interface TransactionSigner
{
    /**
     * 签名
     *
     * @param query
     * @param infoResponse
     * @param blockResponse
     * @param privateKey
     * @return
     */
    TransactionSignResult signTransaction(TransactionQuery query, GetInfoResponse infoResponse, GetBlockResponse blockResponse, String privateKey);
}
