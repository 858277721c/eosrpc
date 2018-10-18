package com.sd.lib.eos.rpc.request;

import com.sd.lib.eos.rpc.request.chain.GetBlockResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 获得区块信息
 */
public class GetBlockRequest extends BaseRequest<GetBlockResponse>
{
    private String block_num_or_id;

    public GetBlockRequest(String baseUrl)
    {
        super(baseUrl);
    }

    public void setBlock_num_or_id(String block_num_or_id)
    {
        this.block_num_or_id = block_num_or_id;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_block";
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("block_num_or_id", block_num_or_id);
        return params;
    }
}
