package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.JsonConverter;
import com.sd.lib.eos.rpc.core.RpcApiExecutor;
import com.sd.lib.eos.rpc.exception.RpcApiExecutorException;
import com.sd.lib.eos.rpc.exception.RpcApiParamsToJsonException;
import com.sd.lib.eos.rpc.exception.RpcApiUnknowCodeException;
import com.sd.lib.eos.rpc.exception.RpcException;
import com.sd.lib.eos.rpc.exception.RpcJsonToObjectException;
import com.sd.lib.eos.rpc.utils.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRequest<P extends BaseRequest.Params, T>
{
    protected final String mBaseUrl;

    public BaseRequest(String baseUrl)
    {
        Utils.checkEmpty(baseUrl, "baseUrl is empty");
        mBaseUrl = baseUrl;
    }

    protected abstract String getPath();

    /**
     * 执行请求(同步执行)
     *
     * @return
     * @throws Exception
     */
    public final ApiResponse<T> execute(final P params) throws RpcException
    {
        final String path = getPath();
        Utils.checkEmpty(path, "path was not specified when execute:" + this);

        final RpcApiExecutor executor = FEOSManager.getInstance().getApiExecutor();
        Utils.checkNotNull(executor, "RpcApiExecutor was not specified when execute:" + this);

        final JsonConverter jsonConverter = FEOSManager.getInstance().getJsonConverter();
        Utils.checkNotNull(jsonConverter, "JsonConverter was not specified when execute:" + this);

        String jsonParams = null;

        if (params != null)
        {
            params.check();
            try
            {
                jsonParams = params.toJson();
            } catch (Exception e)
            {
                throw new RpcApiParamsToJsonException(params + " to json error", e);
            }
        }

        RpcApiExecutor.Result result = null;
        try
        {
            result = executor.execute(mBaseUrl, path, jsonParams);
        } catch (Exception e)
        {
            throw new RpcApiExecutorException("execute " + mBaseUrl + path + " error", e);
        }
        Utils.checkNotNull(result, "RpcApiExecutor return null");

        final int code = result.code;
        if (code <= 0)
            throw new RuntimeException("Illegal result code:" + code);

        final String json = result.string;
        Utils.checkEmpty(json, "RpcApiExecutor return empty string");

        if (code >= 200 && code < 400)
        {
            final Class<T> successClass = getSuccessClass();
            Utils.checkNotNull(successClass, "successful class was not specified when execute:" + this);

            try
            {
                final T success = convertSuccess(json, successClass, jsonConverter, params);
                final ApiResponse apiResponse = new ApiResponse(success);
                return apiResponse;
            } catch (Exception e)
            {
                throw new RpcJsonToObjectException("json to " + successClass.getSimpleName() + " error:", e);
            }
        } else
        {
            if (code == 500)
            {
                try
                {
                    final ErrorResponse error = jsonConverter.jsonToObject(json, ErrorResponse.class);
                    final ApiResponse apiResponse = new ApiResponse(error);
                    return apiResponse;
                } catch (Exception e)
                {
                    throw new RpcJsonToObjectException("json to " + ErrorResponse.class.getSimpleName() + " error", e);
                }
            } else
            {
                throw new RpcApiUnknowCodeException(json, null);
            }
        }
    }

    protected T convertSuccess(String json, Class<T> clazz, JsonConverter converter, P params) throws Exception
    {
        return converter.jsonToObject(json, clazz);
    }

    private Class<T> getSuccessClass()
    {
        final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type[] types = parameterizedType.getActualTypeArguments();
        if (types != null && types.length > 0)
        {
            return (Class<T>) types[types.length - 1];
        } else
        {
            throw new RuntimeException("generic type not found");
        }
    }

    protected static abstract class Params
    {
        public abstract void check();

        public String toJson() throws Exception
        {
            return FEOSManager.getInstance().getJsonConverter().objectToJson(this);
        }
    }
}
