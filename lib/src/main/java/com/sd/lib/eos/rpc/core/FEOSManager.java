package com.sd.lib.eos.rpc.core;

import com.sd.lib.eos.rpc.core.impl.SimpleEccTool;
import com.sd.lib.eos.rpc.core.impl.SimpleJsonConverter;
import com.sd.lib.eos.rpc.core.impl.SimpleRpcApiExecutor;
import com.sd.lib.eos.rpc.core.impl.SimpleTransactionSigner;

public class FEOSManager
{
    private static FEOSManager sInstance;

    private String mBaseUrl;
    private JsonConverter mJsonConverter;
    private RpcApiExecutor mApiExecutor;
    private TransactionSigner mTransactionSigner;
    private EccTool mEccTool;

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

    public void setApiExecutor(RpcApiExecutor apiExecutor)
    {
        mApiExecutor = apiExecutor;
    }

    public void setTransactionSigner(TransactionSigner transactionSigner)
    {
        mTransactionSigner = transactionSigner;
    }

    public void setEccTool(EccTool eccTool)
    {
        mEccTool = eccTool;
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

    public RpcApiExecutor getApiExecutor()
    {
        if (mApiExecutor == null)
            mApiExecutor = new SimpleRpcApiExecutor();
        return mApiExecutor;
    }

    public TransactionSigner getTransactionSigner()
    {
        if (mTransactionSigner == null)
            mTransactionSigner = new SimpleTransactionSigner();
        return mTransactionSigner;
    }

    public EccTool getEccTool()
    {
        if (mEccTool == null)
            mEccTool = new SimpleEccTool();
        return mEccTool;
    }
}
