package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.utils.RpcUtils;

/**
 * 购买内存（按字节购买）
 */
public class BuyrambytesActionParams extends BaseParams<BuyrambytesActionParams.Args, BuyrambytesActionParams.Builder>
{
    private final Args args;

    private BuyrambytesActionParams(Builder builder)
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
        return "buyrambytes";
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
        private final long bytes;

        private Args(Builder builder)
        {
            super(builder);
            this.payer = RpcUtils.checkAccountName(builder.payer, "buyrambytes payer format error");
            this.receiver = RpcUtils.checkAccountName(builder.receiver, "buyrambytes receiver format error");

            final long b = builder.bytes;
            if (b <= 0)
                throw new RuntimeException("buyrambytes bytes must > 0");
            this.bytes = b;
        }

        public String getPayer()
        {
            return payer;
        }

        public String getReceiver()
        {
            return receiver;
        }

        public long getBytes()
        {
            return bytes;
        }
    }

    public static class Builder extends BaseParams.Builder<Builder>
    {
        private String payer;
        private String receiver;
        private long bytes;

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
         * 设置要购买的字节数量
         *
         * @param bytes
         * @return
         */
        public Builder setBytes(long bytes)
        {
            this.bytes = bytes;
            return this;
        }

        public BuyrambytesActionParams build()
        {
            return new BuyrambytesActionParams(this);
        }
    }
}
