package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetCodeResponse;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 查询合约
 */
class GetCodeRequest extends BaseRequest<GetCodeRequest.Params, GetCodeResponse>
{
    public GetCodeRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_code";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String account_name;

        public Params(String account_name)
        {
            this.account_name = account_name;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(account_name, this + " account_name is empty");
        }
    }
}
