package com.sd.lib.eos.rpc.utils;

import java.text.DecimalFormat;

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

    public static String checkQuantity(String quantity, String emptyTip)
    {
        if (isEmpty(quantity))
            throw new IllegalArgumentException(emptyTip);

        final String[] arr = quantity.split(" ");
        if (arr.length != 2)
            throw new IllegalArgumentException("Illegal quantity:" + quantity);

        double num = 0;
        try
        {
            num = Double.parseDouble(arr[0]);
        } catch (Exception e)
        {
            throw new IllegalArgumentException("Illegal quantity:" + quantity);
        }
        if (num < 0)
            throw new IllegalArgumentException("Illegal quantity:" + quantity);

        final String result = new DecimalFormat("#0.0000").format(num) + " " + arr[1];
        return result;
    }
}
