package com.sd.lib.eos.rpc.utils;

public class Utils
{
    public static boolean isEmpty(String content)
    {
        return content == null || content.length() == 0;
    }

    public static String checkEmpty(String content, String exception)
    {
        if (isEmpty(content))
            throw new NullPointerException(exception);
        return content;
    }

    public static <T> T checkNotNull(T object, String exception)
    {
        if (object == null)
            throw new NullPointerException(exception);
        return object;
    }
}
