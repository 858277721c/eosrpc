package com.sd.eos.rpc;

import android.util.Log;

import com.google.gson.Gson;
import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.yanzhenjie.kalle.JsonBody;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleBodyRequest;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.Map;

public class AppRpcApiExecutor implements RpcApiExecutor
{
    private final Gson mGson = new Gson();

    @Override
    public <T> T execute(String baseUrl, String path, Map<String, Object> params, Class<T> clazz) throws Exception
    {
        Log.i(AppRpcApiExecutor.class.getSimpleName(), "submit:" + baseUrl + path);

        final SimpleBodyRequest.Api api = Kalle.post(baseUrl + path);
        if (params != null)
        {
            final String paramsJson = mGson.toJson(params);
            api.body(new JsonBody(paramsJson));

            Log.i(AppRpcApiExecutor.class.getSimpleName(), paramsJson);
        }

        final SimpleResponse<String, Void> response = api.perform(String.class, null);
        final String result = response.succeed();

        Log.i(AppRpcApiExecutor.class.getSimpleName(), "response:" + response.code() + " " + result);

        final T model = mGson.fromJson(result, clazz);
        return model;
    }
}
