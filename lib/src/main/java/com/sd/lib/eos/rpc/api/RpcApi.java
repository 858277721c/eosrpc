package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetCurrencyBalanceResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.List;

public class RpcApi
{
    private final String mBaseUrl;

    public RpcApi()
    {
        this(FEOSManager.getInstance().getBaseUrl());
    }

    public RpcApi(String baseUrl)
    {
        Utils.checkEmpty(baseUrl, "baseUrl is empty");
        mBaseUrl = baseUrl;
    }

    /**
     * 获得区块链信息
     *
     * @return
     * @throws Exception
     */
    public ApiResponse<GetInfoResponse> getInfo() throws Exception
    {
        return new GetInfoRequest(mBaseUrl).execute();
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
        final GetBlockRequest request = new GetBlockRequest(mBaseUrl);
        request.setBlock_num_or_id(block_num_or_id);
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
        final GetAccountRequest request = new GetAccountRequest(mBaseUrl);
        request.setAccount_name(account_name);
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
        final GetCurrencyBalanceRequest request = new GetCurrencyBalanceRequest(mBaseUrl);
        request.setAccount(account);
        request.setSymbol(symbol);
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
        final AbiJsonToBinRequest request = new AbiJsonToBinRequest(mBaseUrl);
        request.setCode(code);
        request.setAction(action);
        request.setArgs(args);
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
        final PushTransactionRequest request = new PushTransactionRequest(mBaseUrl);
        request.setSignatures(signatures);
        request.setCompression(compression);
        request.setPacked_context_free_data(packed_context_free_data);
        request.setPacked_trx(packed_trx);
        return request.execute();
    }
}
