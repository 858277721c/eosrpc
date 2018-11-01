package com.sd.lib.eos.rpc.core.impl;

import com.google.gson.Gson;
import com.sd.lib.eos.rpc.core.JsonConverter;

public class SimpleJsonConverter implements JsonConverter
{
    private final Gson mGson = new Gson();

    @Override
    public String objectToJson(Object object)
    {
        return mGson.toJson(object);
    }

    @Override
    public <T> T jsonToObject(String json, Class<T> clazz) throws Exception
    {
        return mGson.fromJson(json, clazz);
    }
}
