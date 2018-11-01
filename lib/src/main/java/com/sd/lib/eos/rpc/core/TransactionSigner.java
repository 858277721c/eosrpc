package com.sd.lib.eos.rpc.core;

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
     * @param chainId
     * @param privateKey
     * @return
     */
    TransactionSignResult signTransaction(TransactionQuery query, String chainId, String privateKey) throws Exception;
}
