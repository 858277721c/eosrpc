package com.sd.lib.eos.rpc.api.impl;

import com.sd.lib.eos.rpc.api.EosApi;
import com.sd.lib.eos.rpc.response.chain.GetBlockResponse;
import com.sd.lib.http.Request;

/**
 * 获得区块信息
 */
public class GetBlockApi extends EosApi<GetBlockResponse>
{
    private String block_num_or_id;

    @Override
    protected void initRequest(Request request)
    {
        request.setUrlSuffix("/v1/chain/get_block");
        request.getParams().put("block_num_or_id", block_num_or_id);
    }

    public void setBlock_num_or_id(String block_num_or_id)
    {
        this.block_num_or_id = block_num_or_id;
    }
}
