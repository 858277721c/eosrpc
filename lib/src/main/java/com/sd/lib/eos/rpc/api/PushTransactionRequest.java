package com.sd.lib.eos.rpc.api;


import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.List;

/**
 * 提交交易
 */
class PushTransactionRequest extends BaseRequest<PushTransactionRequest.Params, PushTransactionResponse>
{
    public PushTransactionRequest(String baseUrl)
    {
        super(baseUrl);
    }

    @Override
    protected final String getPath()
    {
        return "/v1/chain/push_transaction";
    }

    public static class Params extends BaseRequest.Params
    {
        public final List<String> signatures;
        public final String packed_trx;
        public final String packed_context_free_data;
        public final String compression;

        public Params(List<String> signatures, String packed_trx, String packed_context_free_data, String compression)
        {
            this.signatures = signatures;
            this.packed_trx = packed_trx;
            this.packed_context_free_data = packed_context_free_data == null ? "" : packed_context_free_data;
            this.compression = Utils.isEmpty(compression) ? "none" : compression;
        }

        @Override
        public void check()
        {
            if (signatures == null || signatures.isEmpty())
                throw new NullPointerException(this + " signatures is empty");

            Utils.checkEmpty(packed_trx, this + " packed_trx is empty");
            Utils.checkEmpty(compression, this + " compression is empty");
        }
    }
}
