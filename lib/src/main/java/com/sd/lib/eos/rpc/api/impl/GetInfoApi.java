package com.sd.lib.eos.rpc.api.impl;

import com.sd.lib.eos.rpc.api.EosApi;
import com.sd.lib.eos.rpc.api.model.chain.GetInfoResponse;
import com.sd.lib.http.Request;

/**
 * 获得区块链信息
 */
public class GetInfoApi extends EosApi<GetInfoResponse>
{
    @Override
    protected void initRequest(Request request)
    {
        request.setUrlSuffix("/v1/chain/get_info");
    }
}
