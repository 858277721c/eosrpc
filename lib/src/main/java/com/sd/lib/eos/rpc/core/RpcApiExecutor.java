package com.sd.lib.eos.rpc.core;

import java.util.Map;

/**
 * RPC接口执行器
 */
public interface RpcApiExecutor
{
    /**
     * 请求接口
     *
     * @param baseUrl 接口地址
     * @param path    接口路径
     * @param params  参数
     * @param clazz   返回接口类
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T execute(String baseUrl, String path, Map<String, Object> params, Class<T> clazz) throws Exception;
}
