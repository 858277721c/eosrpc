package com.sd.lib.eos.rpc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RpcUtils
{
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

        if (account.length() != 12)
            throw new RuntimeException("account name lengh must be 12");

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

        final int targetDemicalLength = 4;
        final int demicalLength = arr[0].length() - dotIndex - 1;
        if (demicalLength == targetDemicalLength)
        {
            return moneyString;
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
            throw new RuntimeException("moneyString demical part length is not 4 but " + demicalLength);
        }
    }

    /**
     * 从金额串中获得金额数量
     *
     * @param moneyString
     * @return
     */
    public static double getMoneyAmount(String moneyString)
    {
        if (Utils.isEmpty(moneyString))
            return 0;
        checkMoney(moneyString, null);

        final String item = moneyString.split(" ")[0];
        return new BigDecimal(item).doubleValue();
    }

    /**
     * 从金额串中获得金额类型
     *
     * @param moneyString
     * @return
     */
    public static String getMoneySymbol(String moneyString)
    {
        if (Utils.isEmpty(moneyString))
            return "";
        checkMoney(moneyString, null);

        final String item = moneyString.split(" ")[1];
        return item;
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
     * 格式化金额
     *
     * @param amount 金额数量
     * @param mode
     * @param symbol 币种
     * @return
     */
    public static String formatMoney(double amount, RoundingMode mode, String symbol)
    {
        if (Utils.isEmpty(symbol))
            symbol = "EOS";

        if (mode == null)
            mode = RoundingMode.DOWN;

        final double amountFormat = new BigDecimal(amount).setScale(4, mode).doubleValue();
        return new DecimalFormat("#.0000").format(amountFormat);
    }

    /**
     * 返回指定时间加上指定毫秒后的时间
     *
     * @param time        格式：yyyy-MM-dd'T'HH:mm:ss
     * @param millisecond 指定毫秒
     * @return
     */
    public static String addTime(String time, int millisecond)
    {
        try
        {
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            final Date date = format.parse(time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MILLISECOND, millisecond);

            return format.format(calendar.getTime());
        } catch (ParseException e)
        {
            e.printStackTrace();
            return time;
        }
    }
}
