package com.sd.lib.eos.rpc.params;


import com.sd.lib.eos.rpc.params.model.PermissionModel;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 创建新账号
 */
public class NewaccountActionParams extends BaseParams<NewaccountActionParams.Args, NewaccountActionParams.Builder>
{
    private final Args args;

    private NewaccountActionParams(Builder builder)
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
        return "newaccount";
    }

    @Override
    public final Args getArgs()
    {
        return this.args;
    }

    public static class Args extends ActionParams.Args
    {
        private final String creator;
        private final String name;
        private final PermissionModel owner;
        private final PermissionModel active;

        private final String newact;

        private Args(Builder builder)
        {
            this.creator = RpcUtils.checkAccountName(builder.creator, "newaccount creator was not specified");
            this.name = RpcUtils.checkAccountName(builder.name, "newaccount name was not specified");
            this.owner = Utils.checkNotNull(builder.owner, "newaccount owner permission was not specified");
            this.active = Utils.checkNotNull(builder.active, "newaccount active permission was not specified");

            this.newact = this.name;
        }

        public String getCreator()
        {
            return creator;
        }

        public String getName()
        {
            return name;
        }

        public String getNewact()
        {
            return newact;
        }

        public PermissionModel getOwner()
        {
            return owner;
        }

        public PermissionModel getActive()
        {
            return active;
        }
    }

    public static class Builder extends BaseParams.Builder<Builder>
    {
        private String creator;
        private String name;
        private PermissionModel owner;
        private PermissionModel active;

        /**
         * 设置创建者账号
         *
         * @param creator
         * @return
         */
        public Builder setCreator(String creator)
        {
            this.creator = creator;
            setAuthorization(creator);
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
            this.name = name;
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
            this.owner = PermissionModel.create(publicKey);
            if (active == null)
                setActive(publicKey);
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
            this.active = PermissionModel.create(publicKey);
            return this;
        }

        public NewaccountActionParams build()
        {
            return new NewaccountActionParams(this);
        }
    }
}
