package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetTransactionResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易记录
 */
class GetTransactionRequest extends BaseRequest<GetTransactionResponse>
{
    private String id;

    public GetTransactionRequest(String baseUrl)
    {
        super(baseUrl);
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/history/get_transaction";
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return params;
    }
}
