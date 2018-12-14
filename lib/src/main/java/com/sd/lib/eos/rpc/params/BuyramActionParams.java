package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 购买内存（按EOS购买）
 */
public class BuyramActionParams extends BaseParams<BuyramActionParams.Args, BuyramActionParams.Builder>
{
    private final Args args;

    private BuyramActionParams(Builder builder)
    {
        super(builder);
        setAuthorizationActor(builder.payer);
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

    public static class Args extends BaseParams.Args<Builder>
    {
        private final String payer;
        private final String receiver;
        private final String quant;

        private Args(Builder builder)
        {
            super(builder);
            this.payer = RpcUtils.checkAccountName(builder.payer, "buyram payer format error");
            this.receiver = RpcUtils.checkAccountName(builder.receiver, "buyram receiver format error");
            this.quant = RpcUtils.checkMoney(builder.quantity, "buyram quantity was not specified");
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
        private String quantity;

        /**
         * 设置付款账号
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
         * @param quantity 数量数量
         * @param symbol   币种，默认EOS
         * @return
         */
        public Builder setQuantity(double quantity, String symbol)
        {
            if (Utils.isEmpty(symbol))
                symbol = "EOS";

            this.quantity = RpcUtils.formatMoney(quantity, symbol);
            return this;
        }

        public BuyramActionParams build()
        {
            return new BuyramActionParams(this);
        }
    }
}
