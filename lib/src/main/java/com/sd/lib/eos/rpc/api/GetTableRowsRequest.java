package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetTableRowsResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class GetTableRowsRequest extends BaseRequest<GetTableRowsResponse>
{
    private String scope;
    private String code;
    private String table;
    private boolean json = true;

    private int lower_bound = -1;
    private int upper_bound = -1;
    private int limit = -1;
    private String key_type;
    private int index_position;
    private String encode_type;

    public GetTableRowsRequest(String baseUrl)
    {
        super(baseUrl);
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setTable(String table)
    {
        this.table = table;
    }

    public void setLower_bound(int lower_bound)
    {
        this.lower_bound = lower_bound;
    }

    public void setUpper_bound(int upper_bound)
    {
        this.upper_bound = upper_bound;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public void setKey_type(String key_type)
    {
        this.key_type = key_type;
    }

    public void setIndex_position(int index_position)
    {
        this.index_position = index_position;
    }

    public void setEncode_type(String encode_type)
    {
        this.encode_type = encode_type;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_table_rows";
    }

    @Override
    protected Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("scope", scope);
        params.put("code", code);
        params.put("table", table);
        params.put("json", json);

        if (lower_bound >= 0)
            params.put("lower_bound", lower_bound);

        if (upper_bound >= 0)
            params.put("upper_bound", upper_bound);

        if (limit >= 0)
            params.put("limit", limit);

        if (!Utils.isEmpty(key_type))
            params.put("key_type", key_type);

        if (index_position >= 0)
            params.put("index_position", index_position);

        if (!Utils.isEmpty(encode_type))
            params.put("encode_type", encode_type);

        return params;
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        Utils.checkEmpty(scope, "scope is empty");
        Utils.checkEmpty(code, "code is empty");
        Utils.checkEmpty(table, "table is empty");
    }
}
