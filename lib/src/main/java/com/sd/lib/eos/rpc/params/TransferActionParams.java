package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 转账
 */
public class TransferActionParams extends BaseParams<TransferActionParams.Args, TransferActionParams.Builder>
{
    private final Args args;

    private TransferActionParams(Builder builder)
    {
        super(builder);
        this.args = new Args(builder);
    }

    @Override
    public final String getCode()
    {
        return "eosio.token";
    }

    @Override
    public final String getAction()
    {
        return "transfer";
    }

    @Override
    public final Args getArgs()
    {
        return this.args;
    }

    public static class Args extends ActionParams.Args
    {
        private final String from;
        private final String to;
        private final String quantity;
        private final String memo;

        private Args(Builder builder)
        {
            this.from = builder.from;
            this.to = builder.to;
            this.quantity = builder.quantity;
            this.memo = builder.memo;
        }

        public String getFrom()
        {
            return from;
        }

        public String getTo()
        {
            return to;
        }

        public String getQuantity()
        {
            return quantity;
        }

        public String getMemo()
        {
            return memo;
        }
    }

    public static class Builder extends BaseParams.Builder<Builder>
    {
        private String from;
        private String to;
        private String quantity;
        private String memo;

        /**
         * 设置转账账号
         *
         * @param from
         * @return
         */
        public Builder setFrom(String from)
        {
            this.from = from;
            setAuthorization(from);
            return this;
        }

        /**
         * 设置收款账号
         *
         * @param to
         * @return
         */
        public Builder setTo(String to)
        {
            this.to = to;
            return this;
        }

        /**
         * 设置转账金额
         *
         * @param quantity
         * @return
         */
        public Builder setQuantity(String quantity)
        {
            this.quantity = quantity;
            return this;
        }

        /**
         * 设置转账金额
         *
         * @param quantity 数量
         * @param symbol   币种，默认EOS
         * @return
         */
        public Builder setQuantity(double quantity, String symbol)
        {
            if (Utils.isEmpty(symbol))
                symbol = "EOS";

            return setQuantity(quantity + " " + symbol);
        }

        /**
         * 设置备注
         *
         * @param memo
         * @return
         */
        public Builder setMemo(String memo)
        {
            this.memo = memo;
            return this;
        }

        public TransferActionParams build()
        {
            Utils.checkEmpty(from, "from account was not specified");
            Utils.checkEmpty(to, "to account was not specified");
            Utils.checkEmpty(quantity, "transfer quantity was not specified");
            quantity = Utils.checkQuantity(quantity);

            if (memo == null)
                memo = "";

            return new TransferActionParams(this);
        }
    }
}
