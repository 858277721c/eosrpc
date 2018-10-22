package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.sd.lib.eos.rpc.utils.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class BaseRequest<T>
{
    protected String getBaseUrl()
    {
        return FEOSManager.getInstance().getBaseUrl();
    }

    protected abstract String getPath();

    protected abstract Map<String, Object> getParams();

    /**
     * 执行请求(同步执行)
     *
     * @return
     * @throws Exception
     */
    public final ApiResponse<T> execute() throws Exception
    {
        final String baseUrl = getBaseUrl();
        Utils.checkEmpty(baseUrl, "base url was not specified when execute:" + this);

        final String path = getPath();
        Utils.checkEmpty(path, "path was not specified when execute:" + this);

        final Map<String, Object> params = getParams();

        final Class<T> responseClass = getSuccessClass();
        Utils.checkNotNull(responseClass, "successful class was not specified when execute:" + this);

        final RpcApiExecutor executor = FEOSManager.getInstance().getApiExecutor();
        Utils.checkNotNull(executor, "RpcApiExecutor was not specified when execute:" + this);

        final ApiResponse<T> response = executor.execute(baseUrl, path, params, responseClass);
        Utils.checkNotNull(response, "ApiResponse is null");

        if (!response.isSuccessful())
            Utils.checkNotNull(response.getError(), "ApiResponse is unsuccessful but error is null");

        return response;
    }

    protected Class<T> getSuccessClass()
    {
        final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type[] types = parameterizedType.getActualTypeArguments();
        if (types != null && types.length > 0)
        {
            return (Class<T>) types[0];
        } else
        {
            throw new RuntimeException("generic type not found");
        }
    }
}
