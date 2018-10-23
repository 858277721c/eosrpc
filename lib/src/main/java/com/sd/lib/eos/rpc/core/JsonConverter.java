package com.sd.lib.eos.rpc.core;

public interface JsonConverter
{
    String objectToJson(Object object);

    <T> T jsonToObject(String json, Class<T> clazz);
}
