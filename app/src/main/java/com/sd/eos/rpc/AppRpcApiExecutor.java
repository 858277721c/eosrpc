package com.sd.eos.rpc;

import android.util.Log;

import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.yanzhenjie.kalle.JsonBody;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleBodyRequest;
import com.yanzhenjie.kalle.simple.SimpleResponse;

public class AppRpcApiExecutor implements RpcApiExecutor
{
    @Override
    public Result execute(String baseUrl, String path, String jsonParams) throws Exception
    {
        Log.i(AppRpcApiExecutor.class.getSimpleName(), "execute:" + baseUrl + path);

        final SimpleBodyRequest.Api api = Kalle.post(baseUrl + path);
        if (jsonParams != null)
        {
            api.body(new JsonBody(jsonParams));
            Log.i(AppRpcApiExecutor.class.getSimpleName(), jsonParams);
        }

        final SimpleResponse<String, Void> response = api.perform(String.class, null);
        final int code = response.code();
        final String result = response.succeed();

        Log.i(AppRpcApiExecutor.class.getSimpleName(), "response:" + code + " " + result);
        return new Result(code, result);
    }
}
