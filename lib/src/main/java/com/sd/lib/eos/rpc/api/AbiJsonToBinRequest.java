package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * json转二进制
 */
class AbiJsonToBinRequest extends BaseRequest<AbiJsonToBinRequest.Params, AbiJsonToBinResponse>
{
    public AbiJsonToBinRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/abi_json_to_bin";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String code;
        public final String action;
        public final Object args;

        public Params(String code, String action, Object args)
        {
            this.code = code;
            this.action = action;
            this.args = args;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(code, this + " code is empty");
            Utils.checkEmpty(action, this + " action is empty");
            Utils.checkNotNull(args, this + " args is null");
        }
    }
}
