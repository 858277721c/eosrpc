package com.sd.eos.rpc;

import android.util.Log;

import com.google.gson.Gson;
import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.sd.lib.http.IResponse;
import com.sd.lib.http.Request;
import com.sd.lib.http.impl.httprequest.PostRequest;

import java.util.Map;

public class AppRpcApiExecutor implements RpcApiExecutor
{
    @Override
    public <T> T execute(String baseUrl, String path, Map<String, Object> params, Class<T> clazz) throws Exception
    {
        Log.i(AppRpcApiExecutor.class.getSimpleName(), "execute:" + baseUrl + path);

        Request request = new PostRequest();
        request.setBaseUrl(baseUrl);
        request.setUrlSuffix(path);

        if (params != null)
        {
            for (Map.Entry<String, Object> item : params.entrySet())
            {
                request.getParams().put(item.getKey(), String.valueOf(item.getValue()));
            }

            Log.i(AppRpcApiExecutor.class.getSimpleName(), new Gson().toJson(params));
        }

        final IResponse response = request.execute();
        final String result = response.getAsString();

        final T model = new Gson().fromJson(result, clazz);
        return model;
    }
}
