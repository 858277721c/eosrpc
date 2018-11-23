package com.sd.lib.eos.rpc.core;

public interface EccTool
{
    String generatePrivateKey();

    String privateToPublicKey(String privateKey);

    String sign(byte[] data, String privateKey);
}
