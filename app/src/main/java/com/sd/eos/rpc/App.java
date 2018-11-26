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
                "5KJtdq9xz4fE1sQrBHFDpCpb7EdyK6KJcMRyRLRnQurs1FVyRyf",
                "EOS8YYJTzUTYaS65Mi7NoVrxjUnLGEUQTTYF3JxbMWqYNWz1AgD1s"));

        /**
         * 设置节点地址（必须）
         */
        FEOSManager.getInstance().setBaseUrl("https://jungle2.cryptolions.io:443");
        /**
         * 设置接口请求对象（非必须）
         */
        FEOSManager.getInstance().setApiExecutor(new AppRpcApiExecutor());
    }
}
