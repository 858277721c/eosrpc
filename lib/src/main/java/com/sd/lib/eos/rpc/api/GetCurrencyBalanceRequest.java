package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetCurrencyBalanceResponse;
import com.sd.lib.eos.rpc.core.JsonConverter;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

import org.json.JSONArray;

/**
 * 查看余额
 */
class GetCurrencyBalanceRequest extends BaseRequest<GetCurrencyBalanceRequest.Params, GetCurrencyBalanceResponse>
{
    public GetCurrencyBalanceRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_currency_balance";
    }

    @Override
    protected final GetCurrencyBalanceResponse convertSuccess(String json, Class<GetCurrencyBalanceResponse> clazz, JsonConverter converter, Params params) throws Exception
    {
        final GetCurrencyBalanceResponse response = new GetCurrencyBalanceResponse();

        final JSONArray jsonArray = new JSONArray(json);
        if (jsonArray.length() <= 0)
            response.setBalance("0.0000 " + params.symbol);
        else
            response.setBalance(jsonArray.getString(0));

        return response;
    }

    public static class Params extends BaseRequest.Params
    {
        public final String code;
        public final String account;
        public final String symbol;

        public Params(String code, String account, String symbol)
        {
            this.code = code;
            this.account = account;
            this.symbol = symbol;
        }

        @Override
        public void check()
        {
            RpcUtils.checkAccountName(account, this + " account is empty");
            Utils.checkEmpty(code, this + " code is empty");
            Utils.checkEmpty(symbol, this + " symbol is empty");
        }
    }
}
