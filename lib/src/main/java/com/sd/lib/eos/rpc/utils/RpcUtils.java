package com.sd.lib.eos.rpc.utils;

import android.content.Context;

import com.sd.lib.eos.rpc.api.model.ErrorResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RpcUtils
{
    public static final String EOS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateFormat EOS_DATE_FORMATER = new SimpleDateFormat(EOS_DATE_FORMAT);

    private RpcUtils()
    {
    }

    /**
     * 检查账号名称是否合法
     *
     * @param account
     * @param emptyExcetion
     * @return
     */
    public static String checkAccountName(String account, String emptyExcetion)
    {
        if (Utils.isEmpty(account))
            throw new RuntimeException(emptyExcetion);

        return account;
    }

    /**
     * 检查金额串是否合法
     *
     * @param moneyString
     * @param emptyExcetion
     * @return
     */
    public static String checkMoney(String moneyString, String emptyExcetion)
    {
        if (Utils.isEmpty(moneyString))
            throw new RuntimeException(emptyExcetion);

        final String[] arr = moneyString.split(" ");
        if (arr.length != 2)
            throw new RuntimeException("moneyString must include one blank space:" + moneyString);

        if (Utils.isEmpty(arr[1]))
            throw new RuntimeException("moneyString symbol part is empty");

        double num = 0;
        try
        {
            num = Double.parseDouble(arr[0]);
        } catch (Exception e)
        {
            throw new RuntimeException("moneyString:" + moneyString + " " + e);
        }
        if (num < 0)
            throw new RuntimeException("moneyString is less than 0:" + moneyString);

        final int dotIndex = arr[0].lastIndexOf(".");
        if (dotIndex < 0)
            throw new RuntimeException("moneyString demical part was not found:" + moneyString);

        final int demicalLength = arr[0].length() - dotIndex - 1;
        if (demicalLength != 4)
            throw new RuntimeException("moneyString demical part length is not 4 but " + demicalLength);

        return moneyString;
    }

    /**
     * 从金额串中获得金额数量
     * <p>
     * 1.1234 EOS -> 1.1234
     *
     * @param moneyString
     * @return
     */
    public static double getMoneyAmount(String moneyString)
    {
        try
        {
            return new BigDecimal(moneyString.split(" ")[0]).doubleValue();
        } catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * 从金额串中获得金额类型
     * <p>
     * 1.1234 EOS -> EOS
     *
     * @param moneyString
     * @return
     */
    public static String getMoneySymbol(String moneyString)
    {
        try
        {
            return moneyString.split(" ")[1];
        } catch (Exception e)
        {
            return "";
        }
    }

    /**
     * {@link #formatMoney(double, RoundingMode, String)}
     *
     * @param amount
     * @return
     */
    public static String formatMoney(double amount)
    {
        return formatMoney(amount, null, null);
    }

    /**
     * {@link #formatMoney(double, RoundingMode, String)}
     *
     * @param amount
     * @param symbol
     * @return
     */
    public static String formatMoney(double amount, String symbol)
    {
        return formatMoney(amount, null, symbol);
    }

    /**
     * 格式化金额
     * <p>
     * 1.0 -> 1.0000 symbol
     *
     * @param amount 金额数量
     * @param mode
     * @param symbol
     * @return
     */
    public static String formatMoney(double amount, RoundingMode mode, String symbol)
    {
        if (mode == null)
            mode = RoundingMode.DOWN;

        final NumberFormat format = NumberFormat.getNumberInstance();
        format.setRoundingMode(mode);
        format.setMaximumFractionDigits(4);
        format.setMinimumFractionDigits(4);
        final String amountFormat = format.format(amount);

        if (Utils.isEmpty(symbol))
            return amountFormat;
        else
            return amountFormat + " " + symbol;
    }

    /**
     * 返回某个EOS时间加上指定毫秒后的时间
     *
     * @param time           某个EOS时间（yyyy-MM-dd'T'HH:mm:ss）
     * @param addMilliSecond 要增加的毫秒
     * @return yyyy-MM-dd'T'HH:mm:ss
     */
    public static String addMilliSecond(String time, int addMilliSecond)
    {
        try
        {
            final Date date = EOS_DATE_FORMATER.parse(time);

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MILLISECOND, addMilliSecond);

            return EOS_DATE_FORMATER.format(calendar.getTime());
        } catch (ParseException e)
        {
            e.printStackTrace();
            return time;
        }
    }

    /**
     * EOS时间转为当前时区的日期
     *
     * @param time 格式：yyyy-MM-dd'T'HH:mm:ss
     * @return
     */
    public static Date toDate(String time)
    {
        try
        {
            final DateFormat format = new SimpleDateFormat(EOS_DATE_FORMAT);
            format.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
            final Date date = format.parse(time);
            return date;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getErrorCodeMessage(ErrorResponse errorResponse, Context context)
    {
        final int code = errorResponse.getErrorCode();
        String message = getErrorCodeMessage(code, context);
        if (message == null || message.isEmpty())
            message = errorResponse.getFormattedMessage();
        return message;
    }

    public static String getErrorCodeMessage(int code, Context context)
    {
        final String name = "api_error_code_" + code;

        try
        {
            final int resId = context.getResources().getIdentifier(name, "string", context.getPackageName());
            return context.getResources().getString(resId);
        } catch (Exception e)
        {
            return null;
        }
    }
}
