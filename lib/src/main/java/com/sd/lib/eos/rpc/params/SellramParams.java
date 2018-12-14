package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.utils.RpcUtils;

/**
 * 出售内存
 */
public class SellramParams extends BaseParams<SellramParams.Args, SellramParams.Builder>
{
    private final Args args;

    protected SellramParams(Builder builder)
    {
        super(builder);
        setAuthorizationActor(builder.account);
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
        return "sellram";
    }

    @Override
    public final Args getArgs()
    {
        return this.args;
    }

    public static class Args extends BaseParams.Args<Builder>
    {
        private String account;
        private long bytes;

        private Args(Builder builder)
        {
            super(builder);
            this.account = RpcUtils.checkAccountName(builder.account, "sellram account format error");

            final long b = builder.bytes;
            if (b <= 0)
                throw new RuntimeException("sellram bytes must > 0");
            this.bytes = b;
        }

        public String getAccount()
        {
            return account;
        }

        public long getBytes()
        {
            return bytes;
        }
    }

    public static class Builder extends BaseParams.Builder<Builder>
    {
        private String account;
        private long bytes;

        public Builder setAccount(String account)
        {
            this.account = account;
            return this;
        }

        public Builder setBytes(long bytes)
        {
            this.bytes = bytes;
            return this;
        }

        public SellramParams build()
        {
            return new SellramParams(this);
        }
    }
}
