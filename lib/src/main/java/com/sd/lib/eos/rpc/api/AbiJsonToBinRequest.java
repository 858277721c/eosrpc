package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * json转二进制
 */
class AbiJsonToBinRequest extends BaseRequest<AbiJsonToBinResponse>
{
    private String code;
    private String action;
    private Object args;

    public AbiJsonToBinRequest(String baseUrl)
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

    public void setArgs(Object args)
    {
        this.args = args;
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(code, "code is empty");
        Utils.checkEmpty(action, "action is empty");
        Utils.checkNotNull(args, "args is null");
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/abi_json_to_bin";
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("action", action);
        params.put("args", args);
        return params;
    }
}
