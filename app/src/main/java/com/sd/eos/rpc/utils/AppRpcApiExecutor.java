package com.sd.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.core.impl.SimpleRpcApiExecutor;

public class AppRpcApiExecutor extends SimpleRpcApiExecutor
{
    @Override
    public Result execute(String baseUrl, String path, String jsonParams) throws Exception
    {
        Log.i(AppRpcApiExecutor.class.getSimpleName(), "execute:" + baseUrl + path);
        if (jsonParams != null)
            Log.i(AppRpcApiExecutor.class.getSimpleName(), "params:" + jsonParams);

        final Result result = super.execute(baseUrl, path, jsonParams);
        Log.i(AppRpcApiExecutor.class.getSimpleName(), "response:" + result.code + " " + result.string);

        return result;
    }
}
