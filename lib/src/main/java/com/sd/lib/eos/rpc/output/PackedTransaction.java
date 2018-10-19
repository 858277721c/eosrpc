package com.sd.lib.eos.rpc.output;

import com.sd.lib.eos.rpc.utils.Utils;

public class PackedTransaction
{
    private final String compression;
    private final String packed_trx;

    public PackedTransaction(String compression, String packed_trx)
    {
        Utils.checkEmpty(compression, "");
        Utils.checkEmpty(packed_trx, "");
        this.compression = compression;
        this.packed_trx = packed_trx;
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
