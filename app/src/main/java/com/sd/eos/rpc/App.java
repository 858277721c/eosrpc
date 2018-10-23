package com.sd.eos.rpc;

import android.app.Application;

import com.google.gson.Gson;
import com.sd.eos.rpc.model.AccountHolder;
import com.sd.eos.rpc.model.AccountModel;
import com.sd.eos.rpc.other.AppJsonConverter;
import com.sd.eos.rpc.other.CacheObjectConverter;
import com.sd.eos.rpc.other.KalleGsonConverter;
import com.sd.lib.cache.CacheConfig;
import com.sd.lib.cache.FCache;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.utils.context.FContext;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;

public class App extends Application
{
    private final Gson mGson = new Gson();

    @Override
    public void onCreate()
    {
        super.onCreate();

        FContext.set(this);
        FCache.init(new CacheConfig.Builder()
                .setObjectConverter(new CacheObjectConverter(mGson))
                .build(this));

        Kalle.setConfig(KalleConfig.newBuilder()
                .converter(new KalleGsonConverter(mGson))
                .build());

        FEOSManager.getInstance().setBaseUrl("http://jungle.cryptolions.io:18888");
        FEOSManager.getInstance().setJsonConverter(new AppJsonConverter(mGson));
        FEOSManager.getInstance().setApiExecutor(new AppRpcApiExecutor());
        FEOSManager.getInstance().setTransactionSigner(new AppTransactionSigner());

        AccountHolder.get().add(new AccountModel("fwtest111111",
                "5JQh92zjfWJW6a6suaLm7o2wMCNKZnqp4bb3Kb2dNSb7ts8vDWH",
                "EOS67Q8sXouXtxi72FFgih5hWx7Ks1Bkm8soNuzt9U9sRNuMtjHwT"));
    }
}
