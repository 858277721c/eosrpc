package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.exception.RpcException;

import java.util.Collections;
import java.util.List;

public abstract class SimpleReverseEosActionsLoader extends ReverseEosActionsLoader
{
    private static final int SIZE_RETRY_COUNT = 5;

    private final RpcApi mRpcApi;
    private int mSizeRetryCount;

    public SimpleReverseEosActionsLoader(String accountName, RpcApi rpcApi)
    {
        super(accountName);
        if (rpcApi == null)
            rpcApi = new RpcApi();
        mRpcApi = rpcApi;
    }

    @Override
    protected String getLogTag()
    {
        return SimpleReverseEosActionsLoader.class.getSimpleName();
    }

    @Override
    protected List<GetActionsResponse.Action> loadPageImpl(int position, int pageSize) throws Exception
    {
        final int offset = position < 0 ? -pageSize : -(pageSize - 1);

        List<GetActionsResponse.Action> list = getActions(position, offset);
        if (list == null || list.isEmpty())
            return null;

        final int minSeq = Math.min(list.get(0).getAccount_action_seq(), list.get(list.size() - 1).getAccount_action_seq());
        if (minSeq > 0)
        {
            while (pageSize != list.size())
            {
                if (mSizeRetryCount >= SIZE_RETRY_COUNT)
                {
                    Log.e(getLogTag(), "loadPage size error after " + SIZE_RETRY_COUNT + " retry");
                    mSizeRetryCount = 0;
                    return null;
                }

                mSizeRetryCount++;
                Log.e(getLogTag(), "loadPage expect " + pageSize + " but " + list.size() + " retry:" + mSizeRetryCount);

                list = getActions(position, offset);
                if (list == null)
                    continue;
            }
        }

        mSizeRetryCount = 0;
        Collections.reverse(list);

        return list;
    }

    private List<GetActionsResponse.Action> getActions(int position, int offset) throws RpcException
    {
        Log.i(getLogTag(), "getActions position:" + position + " offset:" + offset);
        final ApiResponse<GetActionsResponse> apiResponse = mRpcApi.getActions(getAccountName(), position, offset);
        if (!apiResponse.isSuccessful())
        {
            Log.e(getLogTag(), "onError:" + apiResponse.getError().getCode() + " " + apiResponse.getError().getMessage());
            onError(apiResponse.getError());
            return null;
        }

        return apiResponse.getSuccess().getActions();
    }

    protected abstract void onError(ErrorResponse errorResponse);
}
