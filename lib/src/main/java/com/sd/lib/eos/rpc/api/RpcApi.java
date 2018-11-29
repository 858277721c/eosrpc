package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.AbiBinToJsonResponse;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
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

    public final String getBaseUrl()
    {
        return mBaseUrl;
    }

    /**
     * 获得区块链信息
     *
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetInfoResponse> getInfo() throws RpcException
    {
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetInfo, new GetInfoRequest(mBaseUrl), null);
        return request.executeInternal(true);
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
        final GetBlockRequest.Params params = new GetBlockRequest.Params(block_num_or_id);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetBlock, new GetBlockRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
        final GetAccountRequest.Params params = new GetAccountRequest.Params(account_name);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetAccount, new GetAccountRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
        return getCurrencyBalance(account, "eosio.token", "eos");
    }

    /**
     * 查看账号余额
     *
     * @param account 账号
     * @param code    合约
     * @param symbol  币种
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetCurrencyBalanceResponse> getCurrencyBalance(String account, String code, String symbol) throws RpcException
    {
        final GetCurrencyBalanceRequest.Params params = new GetCurrencyBalanceRequest.Params(account, code, symbol);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetCurrencyBalance, new GetCurrencyBalanceRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
        final GetActionsRequest.Params params = new GetActionsRequest.Params(account_name, pos, offset);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetActions, new GetActionsRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
        final GetTransactionRequest.Params params = new GetTransactionRequest.Params(id);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetTransaction, new GetTransactionRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
        final GetKeyAccountsRequest.Params params = new GetKeyAccountsRequest.Params(public_key);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetKeyAccounts, new GetKeyAccountsRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
        final GetCodeRequest.Params params = new GetCodeRequest.Params(account_name);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetCode, new GetCodeRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
     * 查询Ram市场
     *
     * @param limit
     * @return
     * @throws RpcException
     */
    public ApiResponse<GetTableRowsResponse> getTableRowsRammarket(int limit) throws RpcException
    {
        return getTableRows("eosio", "eosio", "rammarket", limit);
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
        final GetTableRowsRequest.Params params = new GetTableRowsRequest.Params(scope, code, table, limit);
        final RpcApiRequest request = new RpcApiRequest(ApiType.GetTableRows, new GetTableRowsRequest(mBaseUrl), params);
        return request.executeInternal(true);
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
        final AbiJsonToBinRequest.Params params = new AbiJsonToBinRequest.Params(code, action, args);
        final RpcApiRequest request = new RpcApiRequest(ApiType.AbiJsonToBin, new AbiJsonToBinRequest(mBaseUrl), params);
        return request.executeInternal(true);
    }

    /**
     * 二进制转json
     *
     * @param code
     * @param action
     * @param binargs
     * @return
     * @throws RpcException
     */
    public ApiResponse<AbiBinToJsonResponse> abiBinToJson(String code, String action, String binargs) throws RpcException
    {
        final AbiBinToJsonRequest.Params params = new AbiBinToJsonRequest.Params(code, action, binargs);
        final RpcApiRequest request = new RpcApiRequest(ApiType.AbiBinToJson, new AbiBinToJsonRequest(mBaseUrl), params);
        return request.executeInternal(true);
    }

    /**
     * 推送交易
     *
     * @param signatures
     * @param packed_trx
     * @param packed_context_free_data
     * @param compression
     * @return
     * @throws RpcException
     */
    public ApiResponse<PushTransactionResponse> pushTransaction(List<String> signatures, String packed_trx, String packed_context_free_data, String compression) throws RpcException
    {
        final PushTransactionRequest.Params params = new PushTransactionRequest.Params(signatures, packed_trx, packed_context_free_data, compression);
        final RpcApiRequest request = new RpcApiRequest(ApiType.PushTransaction, new PushTransactionRequest(mBaseUrl), params);
        return request.executeInternal(true);
    }

    protected ApiResponse onErrorResponse(ApiType apiType, ErrorResponse errorResponse, RpcApiRequest request)
    {
        return null;
    }

    protected final class RpcApiRequest<P extends BaseRequest.Params, T>
    {
        private final ApiType mApiType;
        private final BaseRequest<P, T> mRequest;
        private final P mParams;

        private int mExecuteCount;

        public RpcApiRequest(ApiType apiType, BaseRequest<P, T> request, P params)
        {
            mApiType = apiType;
            mRequest = request;
            mParams = params;
        }

        public int getExecuteCount()
        {
            return mExecuteCount;
        }

        public ApiResponse<T> execute() throws RpcException
        {
            return executeInternal(false);
        }

        private ApiResponse<T> executeInternal(boolean notifyError) throws RpcException
        {
            final ApiResponse<T> apiResponse = mRequest.execute(mParams);
            mExecuteCount++;

            if (notifyError)
            {
                if (!apiResponse.isSuccessful())
                {
                    final ApiResponse errorApiResponse = onErrorResponse(mApiType, apiResponse.getError(), this);
                    if (errorApiResponse != null)
                        return errorApiResponse;
                }
            }

            return apiResponse;
        }
    }
}
