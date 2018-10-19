package com.sd.lib.eos.rpc.output.model;

import java.util.ArrayList;
import java.util.List;

public class PushTransactionModel
{
    private List<String> signatures;
    private String compression = "none";
    private String packed_trx;

    public void addSignature(String signature)
    {
        if (signatures == null)
            signatures = new ArrayList<>();

        signatures.add(signature);
    }

    public List<String> getSignatures()
    {
        return signatures;
    }

    public void setSignatures(List<String> signatures)
    {
        this.signatures = signatures;
    }

    public String getCompression()
    {
        return compression;
    }

    public void setCompression(String compression)
    {
        this.compression = compression;
    }

    public String getPacked_trx()
    {
        return packed_trx;
    }

    public void setPacked_trx(String packed_trx)
    {
        this.packed_trx = packed_trx;
    }
}
