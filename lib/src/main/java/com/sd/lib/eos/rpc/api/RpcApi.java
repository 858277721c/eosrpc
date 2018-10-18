package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.request.AbiJsonToBinRequest;
import com.sd.lib.eos.rpc.request.GetBlockRequest;
import com.sd.lib.eos.rpc.request.GetInfoRequest;
import com.sd.lib.eos.rpc.request.chain.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.request.chain.GetBlockResponse;
import com.sd.lib.eos.rpc.request.chain.GetInfoResponse;

public class RpcApi
{
    private final String mBaseUrl;

    public RpcApi(String baseUrl)
    {
        mBaseUrl = baseUrl;
    }

    public GetInfoResponse getInfo() throws Exception
    {
        return new GetInfoRequest(mBaseUrl).execute();
    }

    public GetBlockResponse getBlock(String block_num_or_id) throws Exception
    {
        final GetBlockRequest request = new GetBlockRequest(mBaseUrl);
        request.setBlock_num_or_id(block_num_or_id);
        return request.execute();
    }

    public AbiJsonToBinResponse abiJsonToBin(String code, String action, String args) throws Exception
    {
        final AbiJsonToBinRequest request = new AbiJsonToBinRequest(mBaseUrl);
        request.setCode(code);
        request.setAction(action);
        request.setArgs(args);
        return request.execute();
    }
}
