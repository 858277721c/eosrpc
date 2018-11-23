package com.sd.lib.eos.rpc.core.impl;

import com.sd.lib.eos.rpc.core.EccTool;
import com.sd.lib.eos.rpc.helper.cypto.digest.Sha256;
import com.sd.lib.eos.rpc.helper.cypto.ec.EcDsa;
import com.sd.lib.eos.rpc.helper.cypto.ec.EcSignature;
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

    @Override
    public String sign(byte[] data, String privateKey)
    {
        final Sha256 sha256 = Sha256.from(data);
        final EcSignature signature = EcDsa.sign(sha256, new EosPrivateKey(privateKey));
        return signature.toString();
    }
}
