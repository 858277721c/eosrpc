package com.sd.eos.rpc.other;

import com.google.gson.Gson;
import com.sd.lib.eos.rpc.core.JsonConverter;

public class AppJsonConverter implements JsonConverter
{
    private final Gson mGson;

    public AppJsonConverter(Gson gson)
    {
        mGson = gson;
    }

    @Override
    public String objectToJson(Object object)
    {
        return mGson.toJson(object);
    }

    @Override
    public <T> T jsonToObject(String json, Class<T> clazz)
    {
        return mGson.fromJson(json, clazz);
    }
}
