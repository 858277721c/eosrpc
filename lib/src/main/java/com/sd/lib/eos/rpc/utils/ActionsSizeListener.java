package com.sd.lib.eos.rpc.utils;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.exception.RpcApiExecutorException;
import com.sd.lib.eos.rpc.exception.RpcJsonToObjectException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class ActionsSizeListener
{
    private final ExecutorService mExecutors = Executors.newSingleThreadExecutor();
    private final RpcApi mRpcApi;
    private Future<?> mFuture;

    private int mRetryCount = 1;

    public ActionsSizeListener()
    {
        this(FEOSManager.getInstance().getBaseUrl());
    }

    public ActionsSizeListener(String url)
    {
        mRpcApi = new RpcApi(url);
        resetRetryCount();
    }

    public void check(final String accountName)
    {
        RpcUtils.checkAccountName(accountName, "");

        cancel();
        mFuture = mExecutors.submit(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    final ApiResponse<GetActionsResponse> apiResponse = mRpcApi.getActions(accountName, -1, -1);
                    if (apiResponse.isSuccessful())
                    {
                        resetRetryCount();

                        final List<GetActionsResponse.Action> list = apiResponse.getSuccess().getActions();
                        if (list == null || list.isEmpty())
                            onResult(0);
                        else
                            onResult(list.get(0).getAccount_action_seq() + 1);
                    } else
                    {
                        retryIfNeed(accountName);
                    }
                } catch (RpcApiExecutorException e)
                {
                    e.printStackTrace();
                    retryIfNeed(accountName);
                } catch (RpcJsonToObjectException e)
                {
                    e.printStackTrace();
                    retryIfNeed(accountName);
                }
            }
        });
    }

    private void retryIfNeed(String accountName)
    {
        if (mRetryCount <= 0)
        {
            onResult(-1);
            return;
        }

        mRetryCount--;
        check(accountName);
    }

    private void resetRetryCount()
    {
        mRetryCount = 1;
    }

    protected abstract void onResult(int size);

    public void cancel()
    {
        if (mFuture != null)
        {
            mFuture.cancel(true);
            mFuture = null;
        }
    }
}
