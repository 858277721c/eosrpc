package com.sd.lib.eos.rpc.core.impl;

import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.sd.lib.eos.rpc.utils.HttpRequest;

public class SimpleRpcApiExecutor implements RpcApiExecutor
{
    @Override
    public Result execute(String baseUrl, String path, String jsonParams) throws Exception
    {
        final StringBuilder stringBuilder = new StringBuilder();

        final HttpRequest request = HttpRequest.post(baseUrl + path)
                .trustAllCerts()
                .trustAllHosts()
                .readTimeout(10 * 1000)
                .connectTimeout(10 * 1000);

        if (jsonParams != null)
            request.contentType(HttpRequest.CONTENT_TYPE_JSON).send(jsonParams);

        final int code = request.receive(stringBuilder).code();
        return new Result(code, stringBuilder.toString());
    }
}
