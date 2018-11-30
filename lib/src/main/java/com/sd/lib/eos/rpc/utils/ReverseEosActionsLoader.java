package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.model.GetActionsResponse;

import java.util.List;

public abstract class ReverseEosActionsLoader extends EosActionsLoader
{
    private static final int MAX_POSITION = Integer.MIN_VALUE;

    private int mMaxSize = -1;
    private int mNextPosition = MAX_POSITION;

    public ReverseEosActionsLoader(String accountName)
    {
        super(accountName);
    }

    @Override
    protected String getLogTag()
    {
        return ReverseEosActionsLoader.class.getSimpleName();
    }

    @Override
    public void reset()
    {
        setMaxSize(-1);
        setNextPosition(MAX_POSITION);
        Log.e(getLogTag(), "reset");
    }

    @Override
    public final boolean hasNextPage()
    {
        if (mMaxSize <= 0)
            return false;

        return mNextPosition >= 0;
    }

    @Override
    public final List<GetActionsResponse.Action> loadPage(int pageSize) throws Exception
    {
        if (pageSize <= 0)
            throw new IllegalArgumentException("page size must > 0");

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
        }

        final List<GetActionsResponse.Action> list = loadPageImpl(position, pageSize);
        if (list == null || list.isEmpty())
            return null;

        Log.i(getLogTag(), "loadPage finish:" + list.size());

        final int first = list.get(0).getAccount_action_seq();
        final int last = list.get(list.size() - 1).getAccount_action_seq();

        if (first < last)
            throw new RuntimeException("list must be reverse");

        if (mNextPosition == MAX_POSITION)
        {
            final int maxSeq = Math.max(first, last);
            setMaxSize(maxSeq + 1);
        }

        final int nextPosition = Math.min(first, last) - 1;
        setNextPosition(nextPosition);

        return list;
    }

    protected abstract List<GetActionsResponse.Action> loadPageImpl(int position, int pageSize) throws Exception;

    private void setMaxSize(int maxSize)
    {
        if (mMaxSize != maxSize)
        {
            mMaxSize = maxSize;
            Log.i(getLogTag(), "setMaxSize:" + maxSize);
        }
    }

    private void setNextPosition(int nextPosition)
    {
        if (mNextPosition != nextPosition)
        {
            mNextPosition = nextPosition;
            Log.i(getLogTag(), "setNextPosition:" + nextPosition);
        }
    }
}
