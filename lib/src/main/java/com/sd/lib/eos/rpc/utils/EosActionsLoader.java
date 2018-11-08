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

public abstract class EosActionsLoader
{
    private final String mAccountName;
    private final int mOriginalPosition;
    private final boolean mIsReverse;
    private final RpcApi mRpcApi;

    private int mPosition;

    public EosActionsLoader(String accountName, int position, boolean isReverse, RpcApi rpcApi)
    {
        mAccountName = accountName;
        mOriginalPosition = position;
        mIsReverse = isReverse;
        mRpcApi = rpcApi;
        reset();
    }

    public void reset()
    {
        mPosition = mOriginalPosition;
    }

    public List<GetActionsResponse.Action> loadNextPage(int pageSize) throws RpcJsonToObjectException, RpcApiExecutorException
    {
        pageSize = Math.abs(pageSize);
        final int offset = mIsReverse ? -pageSize : pageSize;

        Log.i(EosActionsLoader.class.getSimpleName(), "loadNextPage position:" + mPosition + " offset:" + offset);

        final List<GetActionsResponse.Action> list = getActions(mPosition, offset);
        if (list == null || list.isEmpty())
            return null;

        Log.i(EosActionsLoader.class.getSimpleName(), "loadNextPage size:" + list.size());

        final int nextPosition = mIsReverse ? list.get(0).getAccount_action_seq() - 1 : list.get(list.size() - 1).getAccount_action_seq() + 1;
        setPosition(nextPosition);

        if (mIsReverse)
            Collections.reverse(list);

        return list;
    }

    private void setPosition(int position)
    {
        if (mPosition != position)
        {
            mPosition = position;
            Log.i(EosActionsLoader.class.getSimpleName(), "setPosition:" + position);
        }
    }

    private List<GetActionsResponse.Action> getActions(int position, int offset) throws RpcJsonToObjectException, RpcApiExecutorException
    {
        final ApiResponse<GetActionsResponse> apiResponse = mRpcApi.getActions(mAccountName, position, offset);
        if (!apiResponse.isSuccessful())
        {
            onError(apiResponse.getError());
            return null;
        }

        return apiResponse.getSuccess().getActions();
    }

    protected abstract void onError(ErrorResponse errorResponse);
}
