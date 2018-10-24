package com.sd.lib.eos.rpc.utils;

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
     * 金额串转double
     *
     * @param moneyString
     * @return
     */
    public double getMoney(String moneyString)
    {
        if (Utils.isEmpty(moneyString))
            return 0;
        checkMoney(moneyString, null);

        return Double.parseDouble(moneyString.split(" ")[0]);
    }
}
