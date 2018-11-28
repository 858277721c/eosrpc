package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 获得区块信息
 */
class GetBlockRequest extends BaseRequest<GetBlockRequest.Params, GetBlockResponse>
{
    public GetBlockRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_block";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String block_num_or_id;

        public Params(String block_num_or_id)
        {
            this.block_num_or_id = block_num_or_id;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(block_num_or_id, this + " block_num_or_id is empty");
        }
    }
}
