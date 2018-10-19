package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.request.AbiJsonToBinRequest;
import com.sd.lib.eos.rpc.api.request.GetBlockRequest;
import com.sd.lib.eos.rpc.api.request.GetInfoRequest;
import com.sd.lib.eos.rpc.api.request.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.request.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.request.model.GetInfoResponse;

public class RpcApi
{
    public GetInfoResponse getInfo() throws Exception
    {
        return new GetInfoRequest().execute();
    }

    public GetBlockResponse getBlock(String block_num_or_id) throws Exception
    {
        final GetBlockRequest request = new GetBlockRequest(block_num_or_id);
        return request.execute();
    }

    public AbiJsonToBinResponse abiJsonToBin(String code, String action, String args) throws Exception
    {
        final AbiJsonToBinRequest request = new AbiJsonToBinRequest(code, action, args);
        return request.execute();
    }
}
