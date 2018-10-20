package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetCurrencyBalanceResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 查看余额
 */
public class GetCurrencyBalanceRequest extends BaseRequest<GetCurrencyBalanceResponse>
{
    private final String code;
    /**
     * 要查询的账号
     */
    private final String account;
    /**
     * 要查询的币种
     */
    private final String symbol;

    public GetCurrencyBalanceRequest(String account)
    {
        this(account, null);
    }

    public GetCurrencyBalanceRequest(String account, String symbol)
    {
        this(null, account, symbol);
    }

    private GetCurrencyBalanceRequest(String code, String account, String symbol)
    {
        Utils.checkEmpty(account, "");

        if (Utils.isEmpty(code))
            code = "eosio.token";

        if (Utils.isEmpty(symbol))
            symbol = "eos";

        this.code = code;
        this.account = account;
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
}
