package com.sd.eos.rpc;

import android.app.Application;

import com.sd.lib.eos.rpc.core.FEOSManager;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        FEOSManager.getInstance().setBaseUrl("http://jungle.cryptolions.io:18888");
        FEOSManager.getInstance().setApiExecutor(new AppRpcApiExecutor());
        FEOSManager.getInstance().setTransactionSigner(new AppTransactionSigner());
    }
}
