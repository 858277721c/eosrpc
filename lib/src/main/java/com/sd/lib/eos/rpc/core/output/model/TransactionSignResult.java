package com.sd.lib.eos.rpc.core.output.model;

import com.sd.lib.eos.rpc.utils.Utils;

import java.util.List;

/**
 * 交易签名结果
 */
public class TransactionSignResult
{
    private final List<String> signatures;
    private final String compression;
    private final String packed_trx;

    public TransactionSignResult(List<String> signatures, String compression, String packed_trx)
    {
        if (signatures == null || signatures.isEmpty())
            throw new IllegalArgumentException("signatures is empty");
        Utils.checkEmpty(compression, "compression is empty");
        Utils.checkEmpty(packed_trx, "packed_trx is empty");

        this.signatures = signatures;
        this.compression = compression;
        this.packed_trx = packed_trx;
    }

    public List<String> getSignatures()
    {
        return signatures;
    }

    public String getCompression()
    {
        return compression;
    }

    public String getPacked_trx()
    {
        return packed_trx;
    }
}
