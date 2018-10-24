package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetCodeResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询合约
 */
class GetCodeRequest extends BaseRequest<GetCodeResponse>
{
    private String account_name;

    public GetCodeRequest(String baseUrl)
    {
        super(baseUrl);
    }

    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_code";
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("account_name", account_name);
        return params;
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(account_name, "account_name is empty");
    }
}
