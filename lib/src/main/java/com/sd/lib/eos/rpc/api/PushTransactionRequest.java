package com.sd.lib.eos.rpc.api;


import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushTransactionRequest extends BaseRequest<PushTransactionResponse>
{
    private final List<String> signatures;
    private final String compression;
    private final String packed_context_free_data;
    private final String packed_trx;

    public PushTransactionRequest(List<String> signatures, String compression, String packed_context_free_data, String packed_trx)
    {
        if (signatures == null || signatures.isEmpty())
            throw new IllegalArgumentException("signatures is empty");

        if (Utils.isEmpty(compression))
            compression = "none";

        if (Utils.isEmpty(packed_context_free_data))
            packed_context_free_data = "";

        Utils.checkEmpty(packed_trx, "");

        this.signatures = signatures;
        this.compression = compression;
        this.packed_context_free_data = packed_context_free_data;
        this.packed_trx = packed_trx;
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/push_transaction";
    }

    @Override
    protected Map<String, Object> getParams()
    {
        final Map<String, Object> params = new HashMap<>();
        params.put("signatures", signatures);
        params.put("compression", compression);
        params.put("packed_context_free_data", packed_context_free_data);
        params.put("packed_trx", packed_trx);
        return params;
    }
}
