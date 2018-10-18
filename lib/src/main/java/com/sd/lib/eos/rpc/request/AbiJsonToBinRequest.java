package com.sd.lib.eos.rpc.request;

import com.sd.lib.eos.rpc.request.chain.AbiJsonToBinResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * json转二进制
 */
public class AbiJsonToBinRequest extends BaseRequest<AbiJsonToBinResponse>
{
    private String code;
    private String action;
    private String args;

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

    /**
     * 设置json数据
     *
     * @param args
     */
    public void setArgs(String args)
    {
        this.args = args;
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
