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
    private static final int MAX_POSITION = Integer.MAX_VALUE;

    private final String mAccountName;
    private final int mOriginalPosition;
    private final RpcApi mRpcApi;

    private int mNextPosition;

    public EosActionsLoader(String accountName, int position, RpcApi rpcApi)
    {
        mAccountName = accountName;
        mOriginalPosition = position;
        mRpcApi = rpcApi;

        reset();
    }

    public int getNextPosition()
    {
        return mNextPosition;
    }

    public void reset()
    {
        setNextPosition(mOriginalPosition < 0 ? MAX_POSITION : mOriginalPosition);
        Log.i(EosActionsLoader.class.getSimpleName(), "reset");
    }

    public List<GetActionsResponse.Action> loadPage(int offset) throws RpcJsonToObjectException, RpcApiExecutorException
    {
        if (offset == 0)
            throw new IllegalArgumentException("Illegal offset:" + offset);

        final boolean isReverse = offset < 0;
        offset = Math.abs(offset);

        int position = 0;
        if (mNextPosition == MAX_POSITION)
        {
            position = -1;
        } else if (mNextPosition < 0)
        {
            return null;
        } else
        {
            position = mNextPosition;
            offset--;
        }

        offset = isReverse ? -offset : offset;

        final List<GetActionsResponse.Action> list = getActions(position, offset);
        if (list == null || list.isEmpty())
            return null;

        Log.i(EosActionsLoader.class.getSimpleName(), "loadPage size:" + list.size());

        final int nextPosition = isReverse ? list.get(0).getAccount_action_seq() - 1 : list.get(list.size() - 1).getAccount_action_seq() + 1;
        setNextPosition(nextPosition);

        if (isReverse)
            Collections.reverse(list);

        return list;
    }

    private void setNextPosition(int position)
    {
        if (mNextPosition != position)
        {
            mNextPosition = position;
            Log.i(EosActionsLoader.class.getSimpleName(), "setNextPosition:" + position);
        }
    }

    private List<GetActionsResponse.Action> getActions(int position, int offset) throws RpcJsonToObjectException, RpcApiExecutorException
    {
        Log.i(EosActionsLoader.class.getSimpleName(), "getActions position:" + position + " offset:" + offset);
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
