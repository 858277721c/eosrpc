package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 获得区块信息
 */
class GetBlockRequest extends BaseRequest<GetBlockResponse>
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

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(block_num_or_id, "block_num_or_id is empty");
    }
}
