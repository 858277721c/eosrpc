package com.sd.lib.eos.rpc.handler;

import com.sd.lib.eos.rpc.output.PushTransaction;
import com.sd.lib.eos.rpc.params.BuyramActionParams;
import com.sd.lib.eos.rpc.params.DelegatebwActionParams;
import com.sd.lib.eos.rpc.params.NewaccountActionParams;

public class CreateAccountHandler
{
    private final NewaccountActionParams mNewaccountActionParams;
    private final BuyramActionParams mBuyramActionParams;
    private final DelegatebwActionParams mDelegatebwActionParams;

    private CreateAccountHandler(Builder builder)
    {
        mNewaccountActionParams = builder.mNewaccountBuilder.build();
        mBuyramActionParams = builder.mBuyramBuilder.build();
        mDelegatebwActionParams = builder.mDelegatebwBuilder.build();
    }

    public final PushTransaction newPushTransaction()
    {
        return new PushTransaction(mNewaccountActionParams, mBuyramActionParams, mDelegatebwActionParams);
    }

    public static class Builder
    {
        private final NewaccountActionParams.Builder mNewaccountBuilder = new NewaccountActionParams.Builder();
        private final BuyramActionParams.Builder mBuyramBuilder = new BuyramActionParams.Builder();
        private final DelegatebwActionParams.Builder mDelegatebwBuilder = new DelegatebwActionParams.Builder();

        /**
         * 设置创建者账号
         *
         * @param creator
         * @return
         */
        public Builder setCreator(String creator)
        {
            mNewaccountBuilder.setCreator(creator);
            mBuyramBuilder.setPayer(creator);
            mDelegatebwBuilder.setFrom(creator);
            return this;
        }

        /**
         * 设置新账号名称
         *
         * @param name
         * @return
         */
        public Builder setName(String name)
        {
            mNewaccountBuilder.setName(name);
            mBuyramBuilder.setReceiver(name);
            mDelegatebwBuilder.setReceiver(name);
            return this;
        }

        /**
         * 设置新账号owner权限公钥
         *
         * @param publicKey
         * @return
         */
        public Builder setOwner(String publicKey)
        {
            mNewaccountBuilder.setOwner(publicKey);
            return this;
        }

        /**
         * 设置新账号active权限公钥
         *
         * @param publicKey
         * @return
         */
        public Builder setActive(String publicKey)
        {
            mNewaccountBuilder.setActive(publicKey);
            return this;
        }

        /**
         * 设置购买内存的金额
         *
         * @param quant
         * @return
         */
        public Builder setBuyRamQuantity(String quant)
        {
            mBuyramBuilder.setQuant(quant);
            return this;
        }

        /**
         * 设置购买内存的金额
         *
         * @param quant  数量
         * @param symbol 币种，默认EOS
         * @return
         */
        public Builder setBuyRamQuantity(double quant, String symbol)
        {
            mBuyramBuilder.setQuant(quant, symbol);
            return this;
        }

        /**
         * 设置网络抵押金额
         *
         * @param stake_net_quantity
         * @return
         */
        public Builder setStake_net_quantity(String stake_net_quantity)
        {
            mDelegatebwBuilder.setStake_net_quantity(stake_net_quantity);
            return this;
        }

        /**
         * 设置cpu抵押金额
         *
         * @param stake_cpu_quantity
         * @return
         */
        public Builder setStake_cpu_quantity(String stake_cpu_quantity)
        {
            mDelegatebwBuilder.setStake_cpu_quantity(stake_cpu_quantity);
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
            mDelegatebwBuilder.setTransfer(transfer);
            return this;
        }

        public CreateAccountHandler build()
        {
            return new CreateAccountHandler(this);
        }
    }
}
