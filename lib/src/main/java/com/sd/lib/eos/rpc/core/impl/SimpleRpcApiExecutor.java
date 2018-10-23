package com.sd.lib.eos.rpc.core.impl;

import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.sd.lib.eos.rpc.utils.HttpRequest;

public class SimpleRpcApiExecutor implements RpcApiExecutor
{
    @Override
    public Result execute(String baseUrl, String path, String jsonParams) throws Exception
    {
        final StringBuilder stringBuilder = new StringBuilder();

        final int code = HttpRequest.post(baseUrl + path)
                .trustAllCerts()
                .trustAllHosts()
                .readTimeout(10 * 1000)
                .connectTimeout(10 * 1000)
                .contentType(HttpRequest.CONTENT_TYPE_JSON)
                .send(jsonParams)
                .receive(stringBuilder)
                .code();

        return new Result(code, stringBuilder.toString());
    }
}
