package com.sd.lib.eos.rpc.request;

import com.sd.lib.eos.rpc.utils.Utils;
import com.sd.lib.http.IResponse;
import com.sd.lib.http.Request;
import com.sd.lib.http.impl.httprequest.PostRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class BaseRequest<T>
{
    private final String mBaseUrl;

    public BaseRequest(String baseUrl)
    {
        Utils.checkEmpty(baseUrl);
        mBaseUrl = baseUrl;
    }

    protected abstract String getPath();

    protected abstract Map<String, Object> getParams();

    public final T execute() throws Exception
    {
        final Request request = new PostRequest();
        request.setBaseUrl(mBaseUrl);
        request.setUrlSuffix(getPath());
        request.getParams().putAll(getParams());

        final IResponse response = request.execute();
        final String content = response.getAsString();

        return Utils.jsonToObject(content, getResponseClass());
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
