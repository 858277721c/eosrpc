package com.sd.lib.eos.rpc.utils;

import com.google.gson.Gson;

public class Utils
{
    public static boolean isEmpty(String content)
    {
        return content == null || content.length() == 0;
    }

    public static void checkEmpty(String content, String exception)
    {
        if (isEmpty(content))
            throw new NullPointerException(exception);
    }

    public static void checkNotNull(Object object, String exception)
    {
        if (object == null)
            throw new NullPointerException(exception);
    }

    public static String objectToJson(Object object)
    {
        return new Gson().toJson(object);
    }

    public static <T> T jsonToObject(String json, Class<T> clazz)
    {
        return new Gson().fromJson(json, clazz);
    }
}
