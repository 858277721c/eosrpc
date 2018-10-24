package com.sd.lib.eos.rpc.api;


import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提交交易
 */
class PushTransactionRequest extends BaseRequest<PushTransactionResponse>
{
    private List<String> signatures;
    private String compression;
    private String packed_context_free_data;
    private String packed_trx;

    public PushTransactionRequest(String baseUrl)
    {
        super(baseUrl);
    }

    public void setSignatures(List<String> signatures)
    {
        this.signatures = signatures;
    }

    public void setCompression(String compression)
    {
        this.compression = compression;
    }

    public void setPacked_context_free_data(String packed_context_free_data)
    {
        this.packed_context_free_data = packed_context_free_data;
    }

    public void setPacked_trx(String packed_trx)
    {
        this.packed_trx = packed_trx;
    }

    @Override
    protected void beforeExecute()
    {
        super.beforeExecute();
        if (signatures == null || signatures.isEmpty())
            throw new IllegalArgumentException("signatures is empty");

        Utils.checkEmpty(packed_trx, "");

        if (Utils.isEmpty(compression))
            compression = "none";

        if (Utils.isEmpty(packed_context_free_data))
            packed_context_free_data = "";
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
