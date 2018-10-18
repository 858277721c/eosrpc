package com.sd.lib.eos.rpc.utils;

import com.google.gson.Gson;

public class Utils
{
    public static boolean isEmpty(String content)
    {
        return content == null || content.length() == 0;
    }

    public static void checkEmpty(String content)
    {
        if (isEmpty(content))
            throw new NullPointerException("");
    }

    public static void checkNull(Object object)
    {
        if (object == null)
            throw new NullPointerException("");
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
