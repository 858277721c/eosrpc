package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetTransactionResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询交易详细
 */
class GetTransactionRequest extends BaseRequest<GetTransactionResponse>
{
    private String id;

    public GetTransactionRequest(String baseUrl)
    {
        super(baseUrl);
    }

    /**
     * 设置交易id
     *
     * @param id
     */
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

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(id, "id is empty");
    }
}
