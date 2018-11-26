package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 赎回抵押资源
 */
public class UnDelegatebwActionParams extends BaseParams<UnDelegatebwActionParams.Args, UnDelegatebwActionParams.Builder>
{
    private final Args args;

    private UnDelegatebwActionParams(Builder builder)
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
        return "undelegatebw";
    }

    @Override
    public final Args getArgs()
    {
        return this.args;
    }

    public static class Args extends BaseParams.Args<Builder>
    {
        private final String from;
        private final String receiver;
        private final String unstake_net_quantity;
        private final String unstake_cpu_quantity;

        private Args(Builder builder)
        {
            super(builder);
            this.from = RpcUtils.checkAccountName(builder.from, "undelegatebw from was not specified");
            this.receiver = RpcUtils.checkAccountName(builder.receiver, "undelegatebw receiver was not specified");
            this.unstake_net_quantity = RpcUtils.checkMoney(builder.unstake_net_quantity, "undelegatebw unstake net quantity was not specified");
            this.unstake_cpu_quantity = RpcUtils.checkMoney(builder.unstake_cpu_quantity, "undelegatebw unstake cpu quantity was not specified");
        }

        public String getFrom()
        {
            return from;
        }

        public String getReceiver()
        {
            return receiver;
        }

        public String getUnstake_net_quantity()
        {
            return unstake_net_quantity;
        }

        public String getUnstake_cpu_quantity()
        {
            return unstake_cpu_quantity;
        }
    }

    public static class Builder extends BaseParams.Builder
    {
        private String from;
        private String receiver;
        private String unstake_net_quantity;
        private String unstake_cpu_quantity;

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
        public Builder setUnstake_net_quantity(double quantity, String symbol)
        {
            if (Utils.isEmpty(symbol))
                symbol = "EOS";

            this.unstake_net_quantity = RpcUtils.formatMoney(quantity, symbol);
            return this;
        }

        /**
         * 设置cpu抵押金额
         *
         * @param quantity 金额数量
         * @param symbol   币种，默认EOS
         * @return
         */
        public Builder setUnstake_cpu_quantity(double quantity, String symbol)
        {
            if (Utils.isEmpty(symbol))
                symbol = "EOS";

            this.unstake_cpu_quantity = RpcUtils.formatMoney(quantity, symbol);
            return this;
        }

        public UnDelegatebwActionParams build()
        {
            return new UnDelegatebwActionParams(this);
        }
    }
}
