package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.GetTableRowsResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import org.json.JSONObject;

public class GetTableRowsRequest extends BaseRequest<GetTableRowsRequest.Params, GetTableRowsResponse>
{
    public GetTableRowsRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/get_table_rows";
    }

    public static class Params extends BaseRequest.Params
    {
        public final String scope;
        public final String code;
        public final String table;

        public final boolean json = true;

        public final int index_position;
        public final int lower_bound;
        public final int upper_bound;
        public final int limit;

        public final String key_type;
        public final String encode_type;

        public Params(String scope, String code, String table, int limit)
        {
            this(scope, code, table, -1, -1, -1, limit, null, null);
        }

        public Params(String scope, String code, String table, int index_position, int lower_bound, int upper_bound, int limit, String key_type, String encode_type)
        {
            this.scope = scope;
            this.code = code;
            this.table = table;
            this.index_position = index_position;
            this.lower_bound = lower_bound;
            this.upper_bound = upper_bound;
            this.limit = limit;
            this.key_type = key_type;
            this.encode_type = encode_type;
        }

        @Override
        public void check()
        {
            Utils.checkEmpty(scope, this + " scope is empty");
            Utils.checkEmpty(code, this + " code is empty");
            Utils.checkEmpty(table, this + " table is empty");
        }

        @Override
        public String toJson() throws Exception
        {
            final JSONObject params = new JSONObject();
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

            return params.toString();
        }
    }
}
