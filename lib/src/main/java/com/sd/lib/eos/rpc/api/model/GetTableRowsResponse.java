package com.sd.lib.eos.rpc.api.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sd.lib.eos.rpc.utils.RpcUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class GetTableRowsResponse
{
    private String rows;
    private boolean more;

    /**
     * 把查询记录转为可赎回的记录
     *
     * @return
     */
    public List<DelbandRow> getDelbandRows()
    {
        try
        {
            final Type type = new TypeToken<List<DelbandRow>>()
            {
            }.getType();

            return new Gson().fromJson(rows, type);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<RammarketRow> getRammarketRow()
    {
        try
        {
            final Type type = new TypeToken<List<RammarketRow>>()
            {
            }.getType();

            return new Gson().fromJson(rows, type);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void setRows(String rows)
    {
        this.rows = rows;
    }

    public boolean isMore()
    {
        return more;
    }

    public void setMore(boolean more)
    {
        this.more = more;
    }

    public String getRows()
    {
        return rows;
    }

    public static class DelbandRow
    {
        private String from;
        private String to;
        private String net_weight;
        private String cpu_weight;

        public String getFrom()
        {
            return from;
        }

        public void setFrom(String from)
        {
            this.from = from;
        }

        public String getTo()
        {
            return to;
        }

        public void setTo(String to)
        {
            this.to = to;
        }

        public String getNet_weight()
        {
            return net_weight;
        }

        public void setNet_weight(String net_weight)
        {
            this.net_weight = net_weight;
        }

        public String getCpu_weight()
        {
            return cpu_weight;
        }

        public void setCpu_weight(String cpu_weight)
        {
            this.cpu_weight = cpu_weight;
        }
    }

    public static class RammarketRow
    {
        private String supply;
        private Balance base;
        private Balance quote;

        /**
         * 返回内存的价格，单位(EOS/KB)
         *
         * @param scale 要保留几位小数
         * @return
         */
        public double getRamPrice(int scale)
        {
            final double quoteAmount = RpcUtils.getMoneyAmount(quote.getBalance());
            final double baseAmount = RpcUtils.getMoneyAmount(base.getBalance());

            final double baseAmountKb = new BigDecimal(baseAmount)
                    .divide(new BigDecimal(1024), 10, RoundingMode.HALF_UP).doubleValue();

            final double price = new BigDecimal(quoteAmount)
                    .divide(new BigDecimal(baseAmountKb), scale, RoundingMode.HALF_UP).doubleValue();

            return price;
        }

        public String getSupply()
        {
            return supply;
        }

        public void setSupply(String supply)
        {
            this.supply = supply;
        }

        public Balance getBase()
        {
            return base;
        }

        public void setBase(Balance base)
        {
            this.base = base;
        }

        public Balance getQuote()
        {
            return quote;
        }

        public void setQuote(Balance quote)
        {
            this.quote = quote;
        }

        public static class Balance
        {
            private String balance;
            private String weight;

            public String getBalance()
            {
                return balance;
            }

            public void setBalance(String balance)
            {
                this.balance = balance;
            }

            public String getWeight()
            {
                return weight;
            }

            public void setWeight(String weight)
            {
                this.weight = weight;
            }
        }
    }
}
