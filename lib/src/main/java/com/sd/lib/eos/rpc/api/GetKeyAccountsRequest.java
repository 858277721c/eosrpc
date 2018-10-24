package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetKeyAccountsResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据公钥查询账号
 */
class GetKeyAccountsRequest extends BaseRequest<GetKeyAccountsResponse>
{
    private String public_key;

    public GetKeyAccountsRequest(String baseUrl)
    {
        super(baseUrl);
    }

    /**
     * 设置要查询的公钥
     *
     * @param public_key
     */
    public void setPublic_key(String public_key)
    {
        this.public_key = public_key;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/history/get_key_accounts";
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(public_key, "public_key is empty");
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("public_key", public_key);
        return null;
    }
}
