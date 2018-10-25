package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.utils.RpcUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询账号信息
 */
class GetAccountRequest extends BaseRequest<GetAccountResponse>
{
    private String account_name;

    public GetAccountRequest(String baseUrl)
    {
        super(baseUrl);
    }

    /**
     * 设置要查询的账号
     *
     * @param account_name
     */
    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_account";
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
        RpcUtils.checkAccountName(account_name, "account_name is empty");
    }
}
