package com.sd.lib.eos.rpc.utils;

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

    public static void checkQuantity(String quantity)
    {
        final String[] arr = quantity.split(" ");
        if (arr.length != 2)
            throw new IllegalArgumentException("Illegal quantity must include one blank space:" + quantity);

        double num = 0;
        try
        {
            num = Double.parseDouble(arr[0]);
        } catch (Exception e)
        {
            throw new IllegalArgumentException("Illegal quantity:" + quantity + " " + e);
        }
        if (num < 0)
            throw new IllegalArgumentException("Illegal quantity less than 0:" + quantity);

        final int dotIndex = arr[0].lastIndexOf(".");
        if (dotIndex < 0)
            throw new IllegalArgumentException("Illegal quantity without demical part:" + quantity);

        final int demicalLength = arr[0].length() - dotIndex - 1;
        if (demicalLength != 4)
            throw new IllegalArgumentException("Illegal quantity demical length is not 4 but " + demicalLength);
    }
}
