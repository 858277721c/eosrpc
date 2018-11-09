package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.exception.RpcApiExecutorException;
import com.sd.lib.eos.rpc.exception.RpcJsonToObjectException;

import java.util.List;

public abstract class EosActionsBoundLoader
{
    private EosActionsLoader mActionsLoader;

    private final String mAccountName;
    private final int mOriginalStart;
    private final int mOriginalEnd;
    private final RpcApi mRpcApi;
    private final boolean mIsReverse;

    private int mMaxSize;
    private int mStart;
    private int mEnd;

    public EosActionsBoundLoader(String accountName, int start, int end, RpcApi rpcApi)
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
            mIsReverse = start > end;
        }

        reset();
    }

    public int getMaxSize()
    {
        return mMaxSize;
    }

    public boolean isReverse()
    {
        return mIsReverse;
    }

    public boolean hasNextPage()
    {
        if (mActionsLoader == null)
            return false;

        final int position = mActionsLoader.getPosition();
        return position != mEnd && mEnd >= 0;
    }

    public void reset()
    {
        mActionsLoader = null;
        mMaxSize = -1;
        mStart = -1;
        mEnd = -1;
        Log.i(EosActionsBoundLoader.class.getSimpleName(), "reset");
    }

    public List<GetActionsResponse.Action> loadPage(int pageSize) throws RpcJsonToObjectException, RpcApiExecutorException
    {
        if (pageSize <= 0)
            throw new IllegalArgumentException("Illegal page size:" + pageSize);

        if (mMaxSize < 0)
        {
            final List<GetActionsResponse.Action> list = new InternalEosActionsLoader(-1).loadPage(-1);
            if (list == null || list.isEmpty())
                return null;

            mMaxSize = list.get(0).getAccount_action_seq() + 1;

            Log.i(EosActionsBoundLoader.class.getSimpleName(), "max size:" + mMaxSize);

            setStart(mOriginalStart);
            setEnd(mOriginalEnd);

            if (mIsReverse)
            {
                if (mOriginalStart < 0 || mOriginalStart >= mMaxSize)
                    setStart(mMaxSize - 1);
            } else
            {
                if (mOriginalEnd < 0 || mOriginalEnd >= mMaxSize)
                    setEnd(mMaxSize - 1);
            }
        }

        if (mMaxSize < 0)
            throw new RuntimeException("max size was not found");

        checkBound();

        if (!hasNextPage())
            return null;

        final int position = mActionsLoader.getPosition();
        final int delta = Math.min(Math.abs(position - mEnd), pageSize);
        final int offset = mIsReverse ? -delta : delta;

        Log.i(EosActionsBoundLoader.class.getSimpleName(), "loadPage position:" + position + " offset:" + offset);

        final List<GetActionsResponse.Action> list = mActionsLoader.loadPage(offset);


        return list;
    }

    private void setStart(int start)
    {
        if (mStart != start)
        {
            mStart = start;
            mActionsLoader = new InternalEosActionsLoader(mStart);
            Log.i(EosActionsBoundLoader.class.getSimpleName(), "setStart:" + start);
        }
    }

    private void setEnd(int end)
    {
        if (mEnd != end)
        {
            mEnd = end;
            Log.i(EosActionsBoundLoader.class.getSimpleName(), "setEnd:" + end);
        }
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
                throw new RuntimeException("bound " + bound + " out of range [0," + (mMaxSize - 1) + "]");
        }
    }

    protected abstract void onError(ErrorResponse errorResponse);

    private class InternalEosActionsLoader extends EosActionsLoader
    {
        public InternalEosActionsLoader(int position)
        {
            super(mAccountName, position, mRpcApi);
        }

        @Override
        protected void onError(ErrorResponse errorResponse)
        {
            EosActionsBoundLoader.this.onError(errorResponse);
        }
    }
}
