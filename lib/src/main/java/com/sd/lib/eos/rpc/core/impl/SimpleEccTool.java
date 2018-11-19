package com.sd.lib.eos.rpc.core.impl;

import com.sd.lib.eos.rpc.core.EccTool;
import com.sd.lib.eos.rpc.helper.cypto.ec.EosPrivateKey;

public class SimpleEccTool implements EccTool
{
    @Override
    public String generatePrivateKey()
    {
        try
        {
            return new EosPrivateKey().toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String privateToPublicKey(String privateKey)
    {
        try
        {
            return new EosPrivateKey(privateKey).getPublicKey().toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
