package com.sd.eos.rpc;

import android.app.Application;

import com.sd.eos.rpc.model.AccountHolder;
import com.sd.eos.rpc.model.AccountModel;
import com.sd.eos.rpc.utils.AppRpcApiExecutor;
import com.sd.eos.rpc.utils.CacheObjectConverter;
import com.sd.lib.cache.CacheConfig;
import com.sd.lib.cache.FCache;
import com.sd.lib.eos.rpc.core.FEOSManager;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        FCache.init(new CacheConfig.Builder()
                .setObjectConverter(new CacheObjectConverter())
                .build(this));

        AccountHolder.get().add(new AccountModel("liuliqin1234",
                "5KbTuZTe6aBmtQ7aV6XxXtjs7Tu84ZZcqr4NAhSCxmvJ7797Kge",
                "EOS6EQbp8Skxc7YQL25NNGWimnCxPCMFYqUSRTWFJm2KeXxM2Hxft"));

        /**
         * 设置节点地址（必须）
         */
        FEOSManager.getInstance().setBaseUrl("http://jungle.cryptolions.io:18888");
        FEOSManager.getInstance().setBaseUrl("https://geo.eosasia.one");
        /**
         * 设置接口请求对象（非必须）
         */
        FEOSManager.getInstance().setApiExecutor(new AppRpcApiExecutor());
    }
}
