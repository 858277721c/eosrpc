package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiBinToJsonResponse;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 二进制转json
 */
class AbiBinToJsonRequest extends BaseRequest<AbiBinToJsonRequest.Params, AbiBinToJsonResponse>
{
    public AbiBinToJsonRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/abi_bin_to_json";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String code;
        public final String action;
        public final String binargs;

        public Params(String code, String action, String binargs)
        {
            this.code = code;
            this.action = action;
            this.binargs = binargs;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(code, this + " code is empty");
            Utils.checkEmpty(action, this + " action is empty");
            Utils.checkNotNull(binargs, this + " binargs is null");
        }
    }
}
