package com.sd.eos.rpc.other;

import com.google.gson.Gson;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

public class KalleGsonConverter implements Converter
{
    private final Gson mGson;

    public KalleGsonConverter(Gson gson)
    {
        mGson = gson;
    }

    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception
    {
        final String jsonStrig = response.body().string();

        S succeedData = null;
        if (succeed == String.class)
            succeedData = (S) jsonStrig;
        else
            succeedData = mGson.fromJson(jsonStrig, succeed);

        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .build();
    }
}