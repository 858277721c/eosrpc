package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetKeyAccountsResponse;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 根据公钥查询账号
 */
class GetKeyAccountsRequest extends BaseRequest<GetKeyAccountsRequest.Params, GetKeyAccountsResponse>
{
    public GetKeyAccountsRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/history/get_key_accounts";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String public_key;

        public Params(String public_key)
        {
            this.public_key = public_key;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(public_key, this + " public_key is empty");
        }
    }
}
