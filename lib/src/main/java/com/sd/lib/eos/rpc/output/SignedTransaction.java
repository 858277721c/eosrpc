package com.sd.lib.eos.rpc.output;

import com.sd.lib.eos.rpc.utils.Utils;

import java.util.List;

public class SignedTransaction
{
    private final List<String> signatures;
    private final String compression;
    private final String packed_trx;

    public SignedTransaction(List<String> signatures, String compression, String packed_trx)
    {
        if (signatures == null || signatures.isEmpty())
            throw new IllegalArgumentException();
        Utils.checkEmpty(compression, "");
        Utils.checkEmpty(packed_trx, "");

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
