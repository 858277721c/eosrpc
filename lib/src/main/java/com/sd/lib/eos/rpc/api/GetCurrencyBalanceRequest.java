package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetCurrencyBalanceResponse;
import com.sd.lib.eos.rpc.core.JsonConverter;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * 查看余额
 */
class GetCurrencyBalanceRequest extends BaseRequest<GetCurrencyBalanceResponse>
{
    private final String code = "eosio.token";
    private String account;
    private String symbol;

    public GetCurrencyBalanceRequest(String baseUrl)
    {
        super(baseUrl);
    }

    /**
     * 设置要查询的账号
     *
     * @param account
     */
    public void setAccount(String account)
    {
        this.account = account;
    }

    /**
     * 设置要查询的币种
     *
     * @param symbol
     */
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_currency_balance";
    }

    @Override
    protected final Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("account", account);
        params.put("symbol", symbol);
        return params;
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        RpcUtils.checkAccountName(account, "account is empty");
        if (Utils.isEmpty(symbol))
            symbol = "eos";
    }

    @Override
    protected final ApiResponse<GetCurrencyBalanceResponse> convertSuccessResponse(String json, Class<GetCurrencyBalanceResponse> clazz, JsonConverter converter) throws Exception
    {
        final JSONArray jsonArray = new JSONArray(json);
        if (jsonArray.length() <= 0)
            throw new RuntimeException("get currency balance error from json:" + json);

        final String balance = jsonArray.getString(0);

        final GetCurrencyBalanceResponse response = new GetCurrencyBalanceResponse();
        response.setBalance(balance);

        return new ApiResponse<>(response);
    }
}
