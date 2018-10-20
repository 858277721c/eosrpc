package com.sd.eos.rpc;

import android.app.Application;

import com.sd.lib.eos.rpc.core.FEOSManager;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Kalle.setConfig(
                KalleConfig.newBuilder()
                        .converter(new GsonConverter())
                        .build()
        );

        FEOSManager.getInstance().setBaseUrl("http://jungle.eosbcn.com:8080");
        FEOSManager.getInstance().setApiExecutor(new AppRpcApiExecutor());
        FEOSManager.getInstance().setTransactionSigner(new AppTransactionSigner());
    }
}
