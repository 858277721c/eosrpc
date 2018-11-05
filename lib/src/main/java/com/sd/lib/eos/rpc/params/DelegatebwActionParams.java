package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 购买抵押资源
 */
public class DelegatebwActionParams extends BaseParams<DelegatebwActionParams.Args, DelegatebwActionParams.Builder>
{
    private final Args args;

    private DelegatebwActionParams(Builder builder)
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
        return "delegatebw";
    }

    @Override
    public final Args getArgs()
    {
        return this.args;
    }

    public static class Args extends ActionParams.Args
    {
        private final String from;
        private final String receiver;
        private final String stake_net_quantity;
        private final String stake_cpu_quantity;

        private final int transfer;

        private Args(Builder builder)
        {
            this.from = RpcUtils.checkAccountName(builder.from, "delegatebw from was not specified");
            this.receiver = RpcUtils.checkAccountName(builder.receiver, "delegatebw receiver was not specified");
            this.stake_net_quantity = RpcUtils.checkMoney(builder.stake_net_quantity, "delegatebw stake net quantity was not specified");
            this.stake_cpu_quantity = RpcUtils.checkMoney(builder.stake_cpu_quantity, "delegatebw stake cpu quantity was not specified");

            final int transfer = builder.transfer;
            if (transfer == 0 || transfer == 1)
                this.transfer = transfer;
            else
                throw new RuntimeException("delegatebw transfer must be 0 or 1");
        }

        public String getFrom()
        {
            return from;
        }

        public String getReceiver()
        {
            return receiver;
        }

        public String getStake_net_quantity()
        {
            return stake_net_quantity;
        }

        public String getStake_cpu_quantity()
        {
            return stake_cpu_quantity;
        }

        public int getTransfer()
        {
            return transfer;
        }
    }

    public static class Builder extends BaseParams.Builder<Builder>
    {
        private String from;
        private String receiver;
        private String stake_net_quantity;
        private String stake_cpu_quantity;

        private int transfer;

        /**
         * 购买者账号
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
         * 接收者账号
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
         * 设置net抵押金额
         *
         * @param quantity 金额数量
         * @param symbol   币种，默认EOS
         * @return
         */
        public Builder setStake_net_quantity(double quantity, String symbol)
        {
            if (Utils.isEmpty(symbol))
                symbol = "EOS";

            this.stake_net_quantity = RpcUtils.formatMoney(quantity, symbol);
            return this;
        }

        /**
         * 设置cpu抵押金额
         *
         * @param quantity 金额数量
         * @param symbol   币种，默认EOS
         * @return
         */
        public Builder setStake_cpu_quantity(double quantity, String symbol)
        {
            if (Utils.isEmpty(symbol))
                symbol = "EOS";

            this.stake_cpu_quantity = RpcUtils.formatMoney(quantity, symbol);
            return this;
        }

        /**
         * 设置是否转移
         *
         * @param transfer 1-转移，0-不转移
         * @return
         */
        public Builder setTransfer(int transfer)
        {
            this.transfer = transfer;
            return this;
        }

        public DelegatebwActionParams build()
        {
            return new DelegatebwActionParams(this);
        }
    }
}
