package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetCurrencyBalanceResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;

import java.util.List;

public class RpcApi
{
    /**
     * 获得区块链信息
     *
     * @return
     * @throws Exception
     */
    public ApiResponse<GetInfoResponse> getInfo() throws Exception
    {
        return new GetInfoRequest().execute();
    }

    /**
     * 获得区块信息
     *
     * @param block_num_or_id
     * @return
     * @throws Exception
     */
    public ApiResponse<GetBlockResponse> getBlock(String block_num_or_id) throws Exception
    {
        final GetBlockRequest request = new GetBlockRequest(block_num_or_id);
        return request.execute();
    }

    /**
     * 获得账号信息
     *
     * @param account_name
     * @return
     * @throws Exception
     */
    public ApiResponse<GetAccountResponse> getAccount(String account_name) throws Exception
    {
        final GetAccountRequest request = new GetAccountRequest(account_name);
        return request.execute();
    }

    /**
     * {@link #getCurrencyBalance(String, String)}
     *
     * @param account
     * @return
     * @throws Exception
     */
    public ApiResponse<GetCurrencyBalanceResponse> getCurrencyBalance(String account) throws Exception
    {
        return getCurrencyBalance(account, "eos");
    }

    /**
     * 查看账号余额
     *
     * @param account 要查看的账号
     * @param symbol  要查看的币种
     * @return
     * @throws Exception
     */
    public ApiResponse<GetCurrencyBalanceResponse> getCurrencyBalance(String account, String symbol) throws Exception
    {
        final GetCurrencyBalanceRequest request = new GetCurrencyBalanceRequest(account, symbol);
        return request.execute();
    }

    /**
     * json转二进制
     *
     * @param code
     * @param action
     * @param args
     * @return
     * @throws Exception
     */
    public ApiResponse<AbiJsonToBinResponse> abiJsonToBin(String code, String action, Object args) throws Exception
    {
        final AbiJsonToBinRequest request = new AbiJsonToBinRequest(code, action, args);
        return request.execute();
    }

    /**
     * 推送交易
     *
     * @param signatures
     * @param compression
     * @param packed_context_free_data
     * @param packed_trx
     * @return
     * @throws Exception
     */
    public ApiResponse<PushTransactionResponse> pushTransaction(List<String> signatures, String compression, String packed_context_free_data, String packed_trx) throws Exception
    {
        final PushTransactionRequest request = new PushTransactionRequest(signatures, compression, packed_context_free_data, packed_trx);
        return request.execute();
    }
}
