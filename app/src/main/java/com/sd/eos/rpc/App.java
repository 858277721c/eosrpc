package com.sd.eos.rpc;

import android.app.Application;

import com.sd.eos.rpc.other.GsonConverter;
import com.sd.eos.rpc.other.GsonObjectConverter;
import com.sd.lib.cache.CacheConfig;
import com.sd.lib.cache.FCache;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.utils.context.FContext;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        FContext.set(this);
        FCache.init(new CacheConfig.Builder()
                .setObjectConverter(new GsonObjectConverter())
                .build(this));

        Kalle.setConfig(KalleConfig.newBuilder()
                .converter(new GsonConverter())
                .build());

        FEOSManager.getInstance().setBaseUrl("http://jungle.eosbcn.com:8080");
        FEOSManager.getInstance().setApiExecutor(new AppRpcApiExecutor());
        FEOSManager.getInstance().setTransactionSigner(new AppTransactionSigner());
    }
}
