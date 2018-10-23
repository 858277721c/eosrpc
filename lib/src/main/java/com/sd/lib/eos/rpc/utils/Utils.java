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

    public static String checkQuantity(String quantity)
    {
        final String[] arr = quantity.split(" ");
        if (arr.length != 2)
            throw new IllegalArgumentException("quantity must include one blank space:" + quantity);

        double num = 0;
        try
        {
            num = Double.parseDouble(arr[0]);
        } catch (Exception e)
        {
            throw new IllegalArgumentException("quantity:" + quantity + " " + e);
        }
        if (num < 0)
            throw new IllegalArgumentException("quantity less than 0:" + quantity);

        final int dotIndex = arr[0].lastIndexOf(".");
        if (dotIndex < 0)
            throw new IllegalArgumentException("quantity without demical part:" + quantity);

        final int targetDemicalLength = 4;
        final int demicalLength = arr[0].length() - dotIndex - 1;
        if (demicalLength == targetDemicalLength)
        {
            return quantity;
        } else if (demicalLength < targetDemicalLength)
        {
            final StringBuilder sb = new StringBuilder(arr[0]);
            final int missLength = targetDemicalLength - demicalLength;
            for (int i = 0; i < missLength; i++)
            {
                sb.append("0");
            }
            sb.append(" ").append(arr[1]);
            return sb.toString();
        } else
        {
            throw new IllegalArgumentException("quantity demical length is not 4 but " + demicalLength);
        }
    }
}
