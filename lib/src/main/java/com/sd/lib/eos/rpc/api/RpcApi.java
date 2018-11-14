package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetCodeResponse;
import com.sd.lib.eos.rpc.api.model.GetCurrencyBalanceResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.GetKeyAccountsResponse;
import com.sd.lib.eos.rpc.api.model.GetTableRowsResponse;
import com.sd.lib.eos.rpc.api.model.GetTransactionResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.exception.RpcException;
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
     * @throws RpcException
     */
    public ApiResponse<GetInfoResponse> getInfo() throws RpcException
    {
        return new GetInfoRequest(mBaseUrl).execute();
    }

    /**
     * 获得区块信息
     *
     * @param block_num_or_id
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetBlockResponse> getBlock(String block_num_or_id) throws RpcException
    {
        final GetBlockRequest request = new GetBlockRequest(mBaseUrl);
        request.setBlock_num_or_id(block_num_or_id);
        return request.execute();
    }

    /**
     * 查询账号信息
     *
     * @param account_name 要查询的账号
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetAccountResponse> getAccount(String account_name) throws RpcException
    {
        final GetAccountRequest request = new GetAccountRequest(mBaseUrl);
        request.setAccount_name(account_name);
        return request.execute();
    }

    /**
     * 查询eos余额
     *
     * @param account
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetCurrencyBalanceResponse> getCurrencyBalance(String account) throws RpcException
    {
        return getCurrencyBalance("eosio.token", account, "eos");
    }

    /**
     * 查看账号余额
     *
     * @param code    合约
     * @param account 账号
     * @param symbol  币种
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetCurrencyBalanceResponse> getCurrencyBalance(String code, String account, String symbol) throws RpcException
    {
        final GetCurrencyBalanceRequest request = new GetCurrencyBalanceRequest(mBaseUrl);
        request.setCode(code);
        request.setAccount(account);
        request.setSymbol(symbol);
        return request.execute();
    }

    /**
     * 查询交易记录
     *
     * @param account_name
     * @param pos
     * @param offset
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetActionsResponse> getActions(String account_name, int pos, int offset) throws RpcException
    {
        final GetActionsRequest request = new GetActionsRequest(mBaseUrl);
        request.setAccount_name(account_name);
        request.setPos(pos);
        request.setOffset(offset);
        return request.execute();
    }

    /**
     * 查询交易详细
     *
     * @param id 交易id
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetTransactionResponse> getTransaction(String id) throws RpcException
    {
        final GetTransactionRequest request = new GetTransactionRequest(mBaseUrl);
        request.setId(id);
        return request.execute();
    }

    /**
     * 根据公钥查询账号
     *
     * @param public_key 公钥
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetKeyAccountsResponse> getKeyAccounts(String public_key) throws RpcException
    {
        final GetKeyAccountsRequest request = new GetKeyAccountsRequest(mBaseUrl);
        request.setPublic_key(public_key);
        return request.execute();
    }

    /**
     * 查询合约
     *
     * @param account_name
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetCodeResponse> getCode(String account_name) throws RpcException
    {
        final GetCodeRequest request = new GetCodeRequest(mBaseUrl);
        request.setAccount_name(account_name);
        return request.execute();
    }

    /**
     * 查询某个账号的可赎回的资源
     *
     * @param scope
     * @param limit
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetTableRowsResponse> getTableRowsDelband(String scope, int limit) throws RpcException
    {
        return getTableRows(scope, "eosio", "delband", limit);
    }

    /**
     * 查询某个账号某个表的记录
     *
     * @param scope 账号
     * @param code  合约
     * @param table 表
     * @param limit 限制返回多少条
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetTableRowsResponse> getTableRows(String scope, String code, String table, int limit) throws RpcException
    {
        final GetTableRowsRequest request = new GetTableRowsRequest(mBaseUrl);
        request.setScope(scope);
        request.setCode(code);
        request.setTable(table);
        request.setLimit(limit);
        return request.execute();
    }

    /**
     * json转二进制
     *
     * @param code
     * @param action
     * @param args
     * @return
     * @throws RpcException
     */
    public ApiResponse<AbiJsonToBinResponse> abiJsonToBin(String code, String action, Object args) throws RpcException
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
     * @throws RpcException
     */
    public ApiResponse<PushTransactionResponse> pushTransaction(List<String> signatures, String compression, String packed_context_free_data, String packed_trx) throws RpcException
    {
        final PushTransactionRequest request = new PushTransactionRequest(mBaseUrl);
        request.setSignatures(signatures);
        request.setCompression(compression);
        request.setPacked_context_free_data(packed_context_free_data);
        request.setPacked_trx(packed_trx);
        return request.execute();
    }
}
