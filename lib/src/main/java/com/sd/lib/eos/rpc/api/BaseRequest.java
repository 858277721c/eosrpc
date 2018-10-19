package com.sd.lib.eos.rpc.api;

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

    public final T execute() throws Exception
    {
        final String baseUrl = getBaseUrl();
        Utils.checkEmpty(baseUrl, "");

        final String path = getPath();
        Utils.checkEmpty(path, "");

        final Map<String, Object> params = getParams();

        final Class<T> responseClass = getResponseClass();
        Utils.checkNotNull(responseClass, "");

        final RpcApiExecutor executor = FEOSManager.getInstance().getApiExecutor();
        final T response = executor.execute(baseUrl, path, params, responseClass);
        return response;
    }

    protected Class<T> getResponseClass()
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
