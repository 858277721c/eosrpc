package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.JsonConverter;
import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.sd.lib.eos.rpc.utils.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

abstract class BaseRequest<T>
{
    protected final String mBaseUrl;

    public BaseRequest(String baseUrl)
    {
        Utils.checkEmpty(baseUrl, "baseUrl is empty");
        mBaseUrl = baseUrl;
    }

    protected abstract String getPath();

    protected abstract Map<String, Object> getParams();

    protected void beforeExecute()
    {
    }

    /**
     * 执行请求(同步执行)
     *
     * @return
     * @throws Exception
     */
    public final ApiResponse<T> execute() throws Exception
    {
        beforeExecute();

        final String path = getPath();
        Utils.checkEmpty(path, "path was not specified when execute:" + this);

        final RpcApiExecutor executor = FEOSManager.getInstance().getApiExecutor();
        Utils.checkNotNull(executor, "RpcApiExecutor was not specified when execute:" + this);

        final JsonConverter jsonConverter = FEOSManager.getInstance().getJsonConverter();
        Utils.checkNotNull(jsonConverter, "JsonConverter was not specified when execute:" + this);

        final Map<String, Object> params = getParams();
        final String jsonParams = params == null ? "" : jsonConverter.objectToJson(params);

        final RpcApiExecutor.Result result = executor.execute(mBaseUrl, path, jsonParams);
        Utils.checkNotNull(result, "RpcApiExecutor return null");

        final int code = result.code;
        if (code <= 0)
            throw new RuntimeException("Illegal result code:" + code);

        final String json = result.string;
        Utils.checkEmpty(json, "RpcApiExecutor return empty string");

        if (code == 500)
        {
            return new ApiResponse<>(jsonConverter.jsonToObject(json, ErrorResponse.class));
        } else
        {
            final Class<T> successClass = getSuccessClass();
            Utils.checkNotNull(successClass, "successful class was not specified when execute:" + this);

            return convertSuccessResponse(json, successClass, jsonConverter);
        }
    }

    protected ApiResponse<T> convertSuccessResponse(String json, Class<T> clazz, JsonConverter converter) throws Exception
    {
        return new ApiResponse<>(converter.jsonToObject(json, clazz));
    }

    private Class<T> getSuccessClass()
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
