package com.sd.lib.eos.rpc.api.impl;

import com.sd.lib.eos.rpc.api.EosApi;
import com.sd.lib.eos.rpc.response.chain.AbiJsonToBinResponse;
import com.sd.lib.http.Request;

/**
 * json转二进制
 */
public class AbiJsonToBinApi extends EosApi<AbiJsonToBinResponse>
{
    private String code;
    private String action;
    private String args;

    @Override
    protected void initRequest(Request request)
    {
        request.setUrlSuffix("/v1/chain/abi_json_to_bin");
        request.getParams()
                .put("code", code)
                .put("action", action)
                .put("args", args);
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
}
