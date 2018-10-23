package com.sd.lib.eos.rpc.core;

import com.sd.lib.eos.rpc.core.impl.SimpleJsonConverter;
import com.sd.lib.eos.rpc.core.impl.SimpleRpcApiExecutor;

public class FEOSManager
{
    private static FEOSManager sInstance;

    private String mBaseUrl;
    private JsonConverter mJsonConverter;
    private RpcApiExecutor mApiExecutor;
    private TransactionSigner mTransactionSigner;

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

    public JsonConverter getJsonConverter()
    {
        if (mJsonConverter == null)
            mJsonConverter = new SimpleJsonConverter();
        return mJsonConverter;
    }

    public void setJsonConverter(JsonConverter jsonConverter)
    {
        mJsonConverter = jsonConverter;
    }

    public RpcApiExecutor getApiExecutor()
    {
        if (mApiExecutor == null)
            mApiExecutor = new SimpleRpcApiExecutor();
        return mApiExecutor;
    }

    public void setApiExecutor(RpcApiExecutor apiExecutor)
    {
        mApiExecutor = apiExecutor;
    }

    public TransactionSigner getTransactionSigner()
    {
        return mTransactionSigner;
    }

    public void setTransactionSigner(TransactionSigner transactionSigner)
    {
        mTransactionSigner = transactionSigner;
    }
}
