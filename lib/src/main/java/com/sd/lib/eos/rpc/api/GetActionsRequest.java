package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class GetActionsRequest extends BaseRequest<GetActionsResponse>
{
    private String account_name;
    private int pos;
    private int offset;

    public GetActionsRequest(String baseUrl)
    {
        super(baseUrl);
    }

    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
    }

    public void setPos(int pos)
    {
        this.pos = pos;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/history/get_actions";
    }

    @Override
    protected Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("account_name", account_name);
        params.put("pos", pos);
        params.put("offset", offset);
        return params;
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(account_name, "account_name was not specified");
    }
}
