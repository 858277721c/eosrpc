package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 查询交易记录
 */
class GetActionsRequest extends BaseRequest<GetActionsRequest.Params, GetActionsResponse>
{
    public GetActionsRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/history/get_actions";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String account_name;
        public final int pos;
        public final int offset;

        public Params(String account_name, int pos, int offset)
        {
            this.account_name = account_name;
            this.pos = pos;
            this.offset = offset;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(account_name, this + " account_name is empty");
        }
    }
}
