package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.exception.RpcApiExecutorException;
import com.sd.lib.eos.rpc.exception.RpcJsonToObjectException;

import java.util.List;

public abstract class EosActionsLoader
{
    private static final String TAG = EosActionsLoader.class.getSimpleName();

    private final String mAccountName;
    private final int mOriginalStart;
    private final int mOriginalEnd;
    private final RpcApi mRpcApi;

    private final boolean mIsReverse;

    private int mPageSize = 100;

    private int mMaxSize;
    private int mStart;
    private int mEnd;
    private int mPosition;
    private int mOffset;

    public EosActionsLoader(String accountName, int start, int end, RpcApi rpcApi)
    {
        if (start < 0 && end < 0)
            throw new IllegalArgumentException("");

        mAccountName = accountName;
        mOriginalStart = start;
        mOriginalEnd = end;
        mRpcApi = rpcApi;

        if (start < 0)
        {
            mIsReverse = true;
        } else if (end < 0)
        {
            mIsReverse = false;
        } else
        {
            mIsReverse = end > start;
        }

        reset();
    }

    public void setPageSize(int pageSize)
    {
        mPageSize = pageSize;
    }

    public int getMaxSize()
    {
        return mMaxSize;
    }

    public void reset()
    {
        mMaxSize = -1;
        mStart = -1;
        mEnd = -1;
        mPosition = -1;
        mOffset = 0;
        Log.i(TAG, "reset");
    }

    public List<GetActionsResponse.Action> loadNextPage() throws RpcJsonToObjectException, RpcApiExecutorException
    {
        if (mMaxSize < 0)
        {
            final List<GetActionsResponse.Action> list = getActions(-1, -1);
            if (list == null || list.isEmpty())
                return null;

            mMaxSize = list.get(0).getAccount_action_seq() + 1;

            Log.i(TAG, "max size:" + mMaxSize);

            setStart(mOriginalStart);
            setEnd(mOriginalEnd);

            if (mIsReverse)
            {
                if (mOriginalStart < 0 || mOriginalStart >= mMaxSize)
                    setStart(mMaxSize - 1);

                setPosition(mStart);
            } else
            {
                setPosition(mStart);
            }
        }

        if (mMaxSize < 0)
            return null;
        if (mOffset == 0)
            return null;

        Log.i(TAG, "loadNextPage position:" + mPosition + " offset:" + mOffset);

        final List<GetActionsResponse.Action> list = getActions(mPosition, mOffset);
        if (list == null || list.isEmpty())
            return null;

        Log.i(TAG, "loadNextPage size:" + list.size());

        final int nextPosition = mIsReverse ? list.get(0).getAccount_action_seq() - 1 : list.get(list.size() - 1).getAccount_action_seq() + 1;
        if (isPositionLegal(nextPosition))
            setPosition(nextPosition);
        else
            setPosition(mEnd);

        return list;
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

    private void setStart(int start)
    {
        Log.i(TAG, "setStart:" + start);
        mStart = start;
    }

    private void setEnd(int end)
    {
        Log.i(TAG, "setEnd:" + end);
        mEnd = end;
    }

    private void setPosition(int position)
    {
        checkBound();
        if (!isPositionLegal(position))
            throw new IllegalArgumentException("Illegal position:" + position + " for bound [" + mStart + "," + mEnd + "]");

        mPosition = position;
        Log.i(TAG, "setPosition:" + position);

        final int delta = Math.min(Math.abs(position - mEnd), mPageSize);
        mOffset = mIsReverse ? -delta : delta;
    }

    private void checkBound()
    {
        checkBound(mStart);
        checkBound(mEnd);

        if (mIsReverse)
        {
            if (mStart < mEnd)
                throw new RuntimeException("Illegal bound [" + mStart + "," + mEnd + "]");
        } else
        {
            if (mStart > mEnd)
                throw new RuntimeException("Illegal bound [" + mStart + "," + mEnd + "]");
        }
    }

    private void checkBound(int bound)
    {
        if (mMaxSize > 0)
        {
            if (bound < 0 || bound >= mMaxSize)
                throw new RuntimeException("bound out of range [0," + (mMaxSize - 1) + "]");
        }
    }

    private boolean isPositionLegal(int position)
    {
        final int min = Math.min(mStart, mEnd);
        final int max = Math.max(mStart, mEnd);

        return position >= min && position <= max;
    }

    protected abstract void onError(ErrorResponse errorResponse);
}
