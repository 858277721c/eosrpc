package com.sd.lib.eos.rpc.api;

import android.util.Log;

import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.exception.RpcException;

public class RetryRpcApi extends RpcApi
{
    private final int mRetryCount;
    private int mCurrentCount;

    public RetryRpcApi(int retryCount)
    {
        this(retryCount, FEOSManager.getInstance().getBaseUrl());
    }

    public RetryRpcApi(int retryCount, String baseUrl)
    {
        super(baseUrl);
        if (retryCount <= 0)
            throw new IllegalArgumentException("retry count must > 0");
        mRetryCount = retryCount;
    }

    @Override
    protected ApiResponse onRpcException(ApiType apiType, RpcException exception, RpcApiRequest request) throws RpcException
    {
        Log.e(RetryRpcApi.class.getSimpleName(), "onRpcException:" + apiType + " " + exception);

        mCurrentCount = 0;
        while (mCurrentCount < mRetryCount)
        {
            try
            {
                mCurrentCount++;

                Log.i(RetryRpcApi.class.getSimpleName(), "retry:" + mCurrentCount);
                final ApiResponse apiResponse = request.execute();
                Log.i(RetryRpcApi.class.getSimpleName(), "retry success");

                return apiResponse;
            } catch (RpcException e)
            {
                Log.i(RetryRpcApi.class.getSimpleName(), "retry failed:" + e);
                continue;
            }
        }

        Log.i(RetryRpcApi.class.getSimpleName(), "retry failed after count:" + mCurrentCount);
        return super.onRpcException(apiType, exception, request);
    }
}
