package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.utils.RpcUtils;

/**
 * 查询账号信息
 */
class GetAccountRequest extends BaseRequest<GetAccountRequest.Params, GetAccountResponse>
{
    public GetAccountRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_account";
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
            RpcUtils.checkAccountName(account_name, this + " account_name is empty");
        }
    }
}
