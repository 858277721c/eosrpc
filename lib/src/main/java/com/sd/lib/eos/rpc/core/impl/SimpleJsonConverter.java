package com.sd.lib.eos.rpc.core.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sd.lib.eos.rpc.core.JsonConverter;

import java.lang.reflect.Type;

public class SimpleJsonConverter implements JsonConverter
{
    private final Gson mGson = new GsonBuilder()
            .registerTypeAdapter(String.class, new JsonDeserializer<String>()
            {
                @Override
                public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
                {
                    if (json.isJsonObject())
                        return json.getAsJsonObject().toString();
                    if (json.isJsonArray())
                        return json.getAsJsonArray().toString();

                    return json.getAsString();
                }
            })
            .create();

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
