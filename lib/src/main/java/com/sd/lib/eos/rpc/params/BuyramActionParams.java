package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 购买内存
 */
public class BuyramActionParams extends BaseParams<BuyramActionParams.Args, BuyramActionParams.Builder>
{
    private final Args args;

    private BuyramActionParams(Builder builder)
    {
        super(builder);
        this.args = new Args(builder);
    }

    @Override
    public final String getCode()
    {
        return "eosio";
    }

    @Override
    public final String getAction()
    {
        return "buyram";
    }

    @Override
    public final Args getArgs()
    {
        return this.args;
    }

    public static class Args extends ActionParams.Args
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

    public static class Builder extends BaseParams.Builder<Builder>
    {
        private String payer;
        private String receiver;
        private String quant;

        /**
         * 设置付款账号
         *
         * @param payer
         * @return
         */
        public Builder setPayer(String payer)
        {
            this.payer = payer;
            setAuthorization(payer);
            return this;
        }

        /**
         * 设置接收账号
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
         * @param quant  数量数量
         * @param symbol 币种，默认EOS
         * @return
         */
        public Builder setQuant(double quant, String symbol)
        {
            if (Utils.isEmpty(symbol))
                symbol = "EOS";

            setQuant(quant + " " + symbol);
            return this;
        }

        /**
         * 设置购买金额
         *
         * @param quant
         * @return
         */
        private Builder setQuant(String quant)
        {
            this.quant = quant;
            return this;
        }

        public BuyramActionParams build()
        {
            Utils.checkEmpty(payer, "payer account was not specified");
            Utils.checkEmpty(receiver, "receiver account was not specified");
            Utils.checkEmpty(quant, "quant was not specified");
            quant = RpcUtils.checkMoney(quant);
            return new BuyramActionParams(this);
        }
    }
}
