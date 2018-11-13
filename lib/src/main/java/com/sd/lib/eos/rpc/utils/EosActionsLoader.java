package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.model.GetActionsResponse;

import java.util.List;

public abstract class EosActionsLoader
{
    private final String mAccountName;
    private final int mOriginalStart;
    private final int mOriginalEnd;
    private final boolean mIsReverse;

    private int mMaxSize;
    private int mStart;
    private int mEnd;

    private int mNextPosition;

    public EosActionsLoader(String accountName, int start, int end)
    {
        if (start < 0 && end < 0)
            throw new IllegalArgumentException("");

        mAccountName = accountName;
        mOriginalStart = start;
        mOriginalEnd = end;

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

    protected String getLogTag()
    {
        return "EosActionsLoader";
    }

    public final String getAccountName()
    {
        return mAccountName;
    }

    public final int getMaxSize()
    {
        return mMaxSize;
    }

    public final boolean isReverse()
    {
        return mIsReverse;
    }

    public final boolean hasNextPage()
    {
        checkInit();

        if (mMaxSize <= 0)
            return false;

        if (mNextPosition < 0 || mEnd < 0)
            return false;

        if (mIsReverse)
        {
            return mNextPosition >= mEnd;
        } else
        {
            return mNextPosition <= mEnd;
        }
    }

    public void reset()
    {
        mMaxSize = -1;
        setStart(-1);
        setEnd(-1);
        setNextPosition(-1);
        Log.e(getLogTag(), "reset");
    }

    public final int init() throws Exception
    {
        if (mMaxSize < 0)
        {
            Log.i(getLogTag(), "start init");
            final int size = initImpl();
            if (size <= 0)
            {
                mMaxSize = 0;
            } else
            {
                mMaxSize = size;
            }

            Log.i(getLogTag(), "init max size:" + mMaxSize);

            if (mMaxSize > 0)
            {
                setStart(mOriginalStart);
                setEnd(mOriginalEnd);

                if (mIsReverse)
                {
                    if (mOriginalStart < 0 || mOriginalStart >= mMaxSize)
                        setStart(mMaxSize - 1);

                    if (mStart < mEnd)
                        throw new RuntimeException("Illegal bound [" + mStart + "," + mEnd + "]");
                } else
                {
                    if (mOriginalEnd < 0 || mOriginalEnd >= mMaxSize)
                        setEnd(mMaxSize - 1);

                    if (mStart > mEnd)
                        throw new RuntimeException("Illegal bound [" + mStart + "," + mEnd + "]");
                }
            }
        }
        return mMaxSize;
    }

    public final List<GetActionsResponse.Action> loadPage(int pageSize) throws Exception
    {
        if (pageSize <= 0)
            throw new IllegalArgumentException("Illegal page size:" + pageSize);

        init();

        if (mMaxSize <= 0)
            return null;

        if (!isBoundLegal(mStart))
        {
            Log.e(getLogTag(), "start bound " + mStart + " out of range [0," + (mMaxSize - 1) + "]");
            return null;
        }
        if (!isBoundLegal(mEnd))
        {
            Log.e(getLogTag(), "end bound " + mEnd + " out of range [0," + (mMaxSize - 1) + "]");
            return null;
        }

        if (!hasNextPage())
            return null;

        final int position = mNextPosition;
        final int size = Math.min(Math.abs(position - mEnd) + 1, pageSize);
        Log.i(getLogTag(), "loadPage position:" + position + " size:" + size);

        final List<GetActionsResponse.Action> list = loadPageImpl(position, size);
        if (list == null || list.isEmpty())
            return null;

        Log.i(getLogTag(), "loadPage size:" + list.size());

        setNextPosition(provideNextPosition(list));
        if (mNextPosition == position)
            throw new RuntimeException("you must change next position after load page succcess");

        Log.i(getLogTag(), "loadPage finish next position:" + mNextPosition);
        return list;
    }

    protected abstract int initImpl() throws Exception;

    protected abstract List<GetActionsResponse.Action> loadPageImpl(int position, int pageSize) throws Exception;

    protected final int provideNextPosition(List<GetActionsResponse.Action> list)
    {
        if (list == null || list.isEmpty())
            throw new IllegalArgumentException("can not provide next position from empty list");

        if (list.size() == 1)
            return list.get(0).getAccount_action_seq() + (isReverse() ? -1 : 1);

        final int startSeq = list.get(0).getAccount_action_seq();
        final int endSeq = list.get(list.size() - 1).getAccount_action_seq();

        if (startSeq == endSeq)
            throw new IllegalArgumentException("list startSeq == endSeq");

        final boolean isReverseList = startSeq > endSeq;

        if (isReverseList)
            return isReverse() ? endSeq - 1 : startSeq + 1;
        else
            return isReverse() ? startSeq - 1 : endSeq + 1;
    }

    private void setStart(int start)
    {
        if (mStart != start)
        {
            mStart = start;
            Log.i(getLogTag(), "setStart:" + start);
            setNextPosition(start);
        }
    }

    private void setEnd(int end)
    {
        if (mEnd != end)
        {
            mEnd = end;
            Log.i(getLogTag(), "setEnd:" + end);
        }
    }

    public final void setNextPosition(int nextPosition)
    {
        if (mNextPosition != nextPosition)
        {
            mNextPosition = nextPosition;
            Log.i(getLogTag(), "setNextPosition:" + nextPosition);
            onNextPositionChanged(nextPosition);
        }
    }

    private boolean isBoundLegal(int bound)
    {
        return bound >= 0 && bound < mMaxSize;
    }

    private void checkInit()
    {
        if (mMaxSize < 0)
            throw new RuntimeException("loader is not initialized");
    }

    protected void onNextPositionChanged(int nextPosition)
    {
    }
}
