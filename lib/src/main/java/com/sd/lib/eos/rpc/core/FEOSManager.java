package com.sd.lib.eos.rpc.core;

public class FEOSManager
{
    private static FEOSManager sInstance;

    private String mBaseUrl;
    private RpcApiExecutor mApiExecutor;

    private FEOSManager()
    {
    }

    public static FEOSManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (FEOSManager.class)
            {
                if (sInstance == null)
                    sInstance = new FEOSManager();
            }
        }
        return sInstance;
    }

    public void setBaseUrl(String baseUrl)
    {
        mBaseUrl = baseUrl;
    }

    public String getBaseUrl()
    {
        return mBaseUrl;
    }

    public RpcApiExecutor getApiExecutor()
    {
        return mApiExecutor;
    }

    public void setApiExecutor(RpcApiExecutor apiExecutor)
    {
        mApiExecutor = apiExecutor;
    }
}
