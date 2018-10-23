package com.sd.lib.eos.rpc.core.impl;

import com.sd.lib.eos.rpc.core.EccTool;
import com.sd.lib.eos.rpc.helper.cypto.ec.EosPrivateKey;

public class SimpleEccTool implements EccTool
{
    @Override
    public String generatePrivateKey()
    {
        return new EosPrivateKey().toString();
    }

    @Override
    public String privateToPublicKey(String privateKey)
    {
        return new EosPrivateKey(privateKey).getPublicKey().toString();
    }
}
