package com.sd.lib.eos.rpc.action.eosio;

/**
 * 购买内存
 */
public class BuyramAction extends EosioAction<BuyramAction.Args>
{
    private final Args args;

    private BuyramAction(Builder builder)
    {
        this.args = new Args(builder);
    }

    @Override
    public final String getAction()
    {
        return "buyram";
    }

    @Override
    public final BuyramAction.Args getArgs()
    {
        return this.args;
    }

    public static class Args
    {
        private final String payer;
        private final String receiver;
        private final String quant;

        private Args(Builder builder)
        {
            this.payer = builder.payer;
            this.receiver = builder.receiver;
            this.quant = builder.quant;
        }

        public String getPayer()
        {
            return payer;
        }

        public String getReceiver()
        {
            return receiver;
        }

        public String getQuant()
        {
            return quant;
        }
    }

    public static class Builder
    {
        private String payer;
        private String receiver;
        private String quant;

        /**
         * 设置付款账户
         *
         * @param payer
         * @return
         */
        public Builder setPayer(String payer)
        {
            this.payer = payer;
            return this;
        }

        /**
         * 设置接收账户
         *
         * @param receiver
         * @return
         */
        public Builder setReceiver(String receiver)
        {
            this.receiver = receiver;
            return this;
        }

        /**
         * 设置购买金额
         *
         * @param quant
         * @return
         */
        public Builder setQuant(String quant)
        {
            this.quant = quant;
            return this;
        }

        public BuyramAction build()
        {
            return new BuyramAction(this);
        }
    }
}
