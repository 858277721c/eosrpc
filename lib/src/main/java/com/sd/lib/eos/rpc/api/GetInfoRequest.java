package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetInfoResponse;

/**
 * 获得区块链信息
 */
class GetInfoRequest extends BaseRequest<GetInfoRequest.Params, GetInfoResponse>
{
    public GetInfoRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_info";
    }

    public static class Params extends BaseRequest.Params
    {
        private Params()
        {
        }

        @Override
        public void check()
        {
        }
    }
}
