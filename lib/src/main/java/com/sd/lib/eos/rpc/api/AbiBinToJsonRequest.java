package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiBinToJsonResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 二进制转json
 */
class AbiBinToJsonRequest extends BaseRequest<AbiBinToJsonResponse>
{
    private String code;
    private String action;
    private String binargs;

    public AbiBinToJsonRequest(String baseUrl)
    {
        super(baseUrl);
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public void setBinargs(String binargs)
    {
        this.binargs = binargs;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/abi_bin_to_json";
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("action", action);
        params.put("binargs", binargs);
        return params;
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(code, "code is empty");
        Utils.checkEmpty(action, "action is empty");
        Utils.checkNotNull(binargs, "binargs is null");
    }
}
