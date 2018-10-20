package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * json转二进制
 */
public class AbiJsonToBinRequest extends BaseRequest<AbiJsonToBinResponse>
{
    private final String code;
    private final String action;
    private final Object args;

    public AbiJsonToBinRequest(String code, String action, Object args)
    {
        Utils.checkEmpty(code, "");
        Utils.checkEmpty(action, "");
        Utils.checkNotNull(args, "");

        this.code = code;
        this.action = action;
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
