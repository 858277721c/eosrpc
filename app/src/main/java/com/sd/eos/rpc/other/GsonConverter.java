package com.sd.eos.rpc.other;

import com.google.gson.Gson;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

public class GsonConverter implements Converter
{
    private static final Gson GSON = new Gson();

    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception
    {
        final String jsonStrig = response.body().string();

        S succeedData = null;
        if (succeed == String.class)
            succeedData = (S) jsonStrig;
        else
            succeedData = GSON.fromJson(jsonStrig, succeed);

        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .build();
    }
}