package com.sd.lib.eos.rpc.api.request;

import com.sd.lib.eos.rpc.api.request.model.GetInfoResponse;

import java.util.Map;

/**
 * 获得区块链信息
 */
public class GetInfoRequest extends BaseRequest<GetInfoResponse>
{
    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_info";
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        return null;
    }
}
