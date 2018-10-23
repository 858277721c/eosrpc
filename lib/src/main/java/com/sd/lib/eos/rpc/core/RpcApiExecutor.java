package com.sd.lib.eos.rpc.core;

/**
 * RPC接口执行器
 */
public interface RpcApiExecutor
{
    /**
     * 请求接口(POST执行)
     *
     * @param baseUrl    接口地址
     * @param path       接口路径
     * @param jsonParams 参数
     * @return
     * @throws Exception
     */
    Result execute(String baseUrl, String path, String jsonParams) throws Exception;

    class Result
    {
        public final int code;
        public final String string;

        public Result(int code, String string)
        {
            this.code = code;
            this.string = string;
        }
    }
}
