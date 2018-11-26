package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.params.model.PermissionModel;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.eos.rpc.utils.Utils;

/**
 * 修改权限密钥
 */
public class UpdateauthParams extends BaseParams<UpdateauthParams.Args, UpdateauthParams.Builder>
{
    private final Args args;

    protected UpdateauthParams(Builder builder)
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
        return "updateauth";
    }

    @Override
    public final Args getArgs()
    {
        return this.args;
    }

    public static class Args extends ActionParams.Args
    {
        private String account;
        private String permission;
        private String parent;
        private PermissionModel auth;

        private Args(Builder builder)
        {
            this.account = RpcUtils.checkAccountName(builder.account, "updateauth account was not specified");
            this.permission = Utils.checkEmpty(builder.permission, "updateauth permission was not specified");
            this.parent = Utils.checkNotNull(builder.parent, "updateauth parent was not specified");

            final String publicKey = Utils.checkEmpty(builder.publicKey, "updateauth new public key was not specified");
            this.auth = PermissionModel.create(publicKey);
        }

        public String getAccount()
        {
            return account;
        }

        public String getPermission()
        {
            return permission;
        }

        public String getParent()
        {
            return parent;
        }

        public PermissionModel getAuth()
        {
            return auth;
        }
    }

    public static class Builder extends BaseParams.Builder<Builder>
    {
        private String account;
        private String permission;
        private String parent;
        private String publicKey;

        /**
         * 设置要修改的账号
         *
         * @param account
         * @return
         */
        public Builder setAccount(String account)
        {
            this.account = account;
            setAuthorization(account, "owner");
            return this;
        }

        /**
         * 设置要修改的权限，例如"owner"，"active"
         *
         * @param permission
         * @return
         */
        public Builder setPermission(String permission)
        {
            this.permission = permission;
            if ("owner".equals(permission))
            {
                this.parent = "";
            } else
            {
                this.parent = "owner";
            }

            return this;
        }

        /**
         * 设置要修改的权限对应的新公钥
         *
         * @param publicKey
         * @return
         */
        public Builder setPublicKey(String publicKey)
        {
            this.publicKey = publicKey;
            return this;
        }

        public UpdateauthParams build()
        {
            return new UpdateauthParams(this);
        }
    }
}
