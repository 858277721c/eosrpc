package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取账号信息
 */
class GetAccountRequest extends BaseRequest<GetAccountResponse>
{
    private String account_name;

    public GetAccountRequest(String account_name)
    {
        Utils.checkEmpty(account_name, "");
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
}
