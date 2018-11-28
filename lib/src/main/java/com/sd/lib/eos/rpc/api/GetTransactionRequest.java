package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetTransactionResponse;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 查询交易详细
 */
class GetTransactionRequest extends BaseRequest<GetTransactionRequest.Params, GetTransactionResponse>
{
    public GetTransactionRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/history/get_transaction";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String id;

        public Params(String id)
        {
            this.id = id;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(id, this + " id is empty");
        }
    }
}
