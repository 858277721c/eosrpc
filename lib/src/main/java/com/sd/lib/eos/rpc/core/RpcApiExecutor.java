package com.sd.lib.eos.rpc.core;

import java.util.Map;

public interface RpcApiExecutor
{
    <T> T execute(String baseUrl, String path, Map<String, Object> params, Class<T> clazz);
}
