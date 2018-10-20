package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;

import java.util.List;

public class RpcApi
{
    public GetInfoResponse getInfo() throws Exception
    {
        return new GetInfoRequest().execute();
    }

    public GetBlockResponse getBlock(String block_num_or_id) throws Exception
    {
        final GetBlockRequest request = new GetBlockRequest(block_num_or_id);
        return request.execute();
    }

    public GetAccountResponse getAccount(String account_name) throws Exception
    {
        final GetAccountRequest request = new GetAccountRequest(account_name);
        return request.execute();
    }

    public AbiJsonToBinResponse abiJsonToBin(String code, String action, Object args) throws Exception
    {
        final AbiJsonToBinRequest request = new AbiJsonToBinRequest(code, action, args);
        return request.execute();
    }

    public PushTransactionResponse pushTransaction(List<String> signatures, String compression, String packed_context_free_data, String packed_trx) throws Exception
    {
        final PushTransactionRequest request = new PushTransactionRequest(signatures, compression, packed_context_free_data, packed_trx);
        return request.execute();
    }
}
