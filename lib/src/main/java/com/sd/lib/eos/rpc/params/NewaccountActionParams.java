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
        setAuthorizationActor(builder.creator);
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

    public static class Args extends BaseParams.Args<Builder>
    {
        private final String creator;
        private final String name;
        private final PermissionModel owner;
        private final PermissionModel active;

        private final String newact;

        private Args(Builder builder)
        {
            super(builder);
            this.creator = RpcUtils.checkAccountName(builder.creator, "newaccount creator was not specified");
            this.name = RpcUtils.checkAccountName(builder.newAccount, "newaccount name was not specified");

            final String ownerPublicKey = Utils.checkEmpty(builder.ownerPublicKey, "newaccount owner public key was not specified");
            this.owner = PermissionModel.create(ownerPublicKey);

            final String activePublicKey = Utils.checkEmpty(builder.activePublicKey, "newaccount active public key was not specified");
            this.active = PermissionModel.create(activePublicKey);

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
        private String newAccount;

        private String ownerPublicKey;
        private String activePublicKey;

        /**
         * 设置创建者账号
         *
         * @param creator
         * @return
         */
        public Builder setCreator(String creator)
        {
            this.creator = creator;
            return this;
        }

        /**
         * 设置新账号名称
         *
         * @param account
         * @return
         */
        public Builder setNewAccount(String account)
        {
            this.newAccount = account;
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
            this.ownerPublicKey = publicKey;
            if (Utils.isEmpty(activePublicKey))
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
            this.activePublicKey = publicKey;
            return this;
        }

        public NewaccountActionParams build()
        {
            return new NewaccountActionParams(this);
        }
    }
}
