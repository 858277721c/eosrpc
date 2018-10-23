package com.sd.eos.rpc;

import android.app.Application;

import com.sd.eos.rpc.model.AccountHolder;
import com.sd.eos.rpc.model.AccountModel;
import com.sd.eos.rpc.other.CacheObjectConverter;
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

        AccountHolder.get().add(new AccountModel("fwtest111111",
                "5JQh92zjfWJW6a6suaLm7o2wMCNKZnqp4bb3Kb2dNSb7ts8vDWH",
                "EOS67Q8sXouXtxi72FFgih5hWx7Ks1Bkm8soNuzt9U9sRNuMtjHwT"));

        /**
         * 设置节点地址（必须）
         */
        FEOSManager.getInstance().setBaseUrl("http://jungle.cryptolions.io:18888");
        /**
         * 设置接口请求对象（非必须）
         */
        FEOSManager.getInstance().setApiExecutor(new AppRpcApiExecutor());
        /**
         * 设置交易签名对象（必须）
         */
        FEOSManager.getInstance().setTransactionSigner(new AppTransactionSigner());
    }
}
