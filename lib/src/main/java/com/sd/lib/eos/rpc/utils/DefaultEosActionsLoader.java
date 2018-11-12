package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.exception.RpcApiExecutorException;
import com.sd.lib.eos.rpc.exception.RpcJsonToObjectException;

import java.util.Collections;
import java.util.List;

public abstract class DefaultEosActionsLoader extends BaseEosActionsLoader
{
    private static final int MAX_RETRY_COUNT = 5;

    private final RpcApi mRpcApi;
    private int mRetryCount;

    public DefaultEosActionsLoader(String accountName, int start, int end, RpcApi rpcApi)
    {
        super(accountName, start, end);
        if (rpcApi == null)
            rpcApi = new RpcApi();
        mRpcApi = rpcApi;
    }

    @Override
    protected int initImpl() throws Exception
    {
        final List<GetActionsResponse.Action> list = getActions(-1, -1);
        if (list == null || list.isEmpty())
            return 0;

        return list.get(0).getAccount_action_seq() + 1;
    }

    @Override
    protected List<GetActionsResponse.Action> loadPageImpl(final int position, final int pageSize) throws Exception
    {
        final int realPageSize = pageSize - 1;
        final int offset = isReverse() ? -realPageSize : realPageSize;
        List<GetActionsResponse.Action> list = getActions(position, offset);
        if (list == null || list.isEmpty())
            return null;

        while (pageSize != list.size())
        {
            if (mRetryCount >= MAX_RETRY_COUNT)
            {
                Log.e(DefaultEosActionsLoader.class.getSimpleName(), "loadPage size error after " + MAX_RETRY_COUNT + " retry");
                mRetryCount = 0;
                return null;
            }

            mRetryCount++;
            Log.e(DefaultEosActionsLoader.class.getSimpleName(), "loadPage expect " + pageSize + " but " + list.size() + " retry:" + mRetryCount);

            list = getActions(position, offset);
            if (list == null)
                continue;
        }
        mRetryCount = 0;

        if (isReverse())
            Collections.reverse(list);

        return list;
    }

    @Override
    protected int provideNextPosition(List<GetActionsResponse.Action> list)
    {
        final int lastSeq = list.get(list.size() - 1).getAccount_action_seq();
        return isReverse() ? lastSeq - 1 : lastSeq + 1;
    }

    private List<GetActionsResponse.Action> getActions(int position, int offset) throws RpcJsonToObjectException, RpcApiExecutorException
    {
        Log.i(DefaultEosActionsLoader.class.getSimpleName(), "getActions position:" + position + " offset:" + offset);
        final ApiResponse<GetActionsResponse> apiResponse = mRpcApi.getActions(getAccountName(), position, offset);
        if (!apiResponse.isSuccessful())
        {
            Log.e(DefaultEosActionsLoader.class.getSimpleName(), "onError:" + apiResponse.getError().getCode() + " " + apiResponse.getError().getMessage());
            onError(apiResponse.getError());
            return null;
        }

        return apiResponse.getSuccess().getActions();
    }

    protected abstract void onError(ErrorResponse errorResponse);
}
