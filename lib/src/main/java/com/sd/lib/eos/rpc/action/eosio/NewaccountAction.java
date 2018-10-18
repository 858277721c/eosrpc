package com.sd.lib.eos.rpc.action.eosio;

import com.sd.lib.eos.rpc.action.model.PermissionModel;

/**
 * 创建新账号
 */
public class NewaccountAction extends EosioAction
{
    private String creator;
    private String name;
    private PermissionModel owner;
    private PermissionModel active;

    private NewaccountAction(Builder builder)
    {
        super("newaccount");
        this.creator = builder.creator;
        this.name = builder.name;
        this.owner = builder.owner;
        this.active = builder.active;
    }

    public static class Builder
    {
        private String creator;
        private String name;
        private PermissionModel owner;
        private PermissionModel active;

        public Builder setCreator(String creator)
        {
            this.creator = creator;
            return this;
        }

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setOwner(PermissionModel owner)
        {
            this.owner = owner;
            return this;
        }

        public Builder setActive(PermissionModel active)
        {
            this.active = active;
            return this;
        }

        public NewaccountAction build()
        {
            return new NewaccountAction(this);
        }
    }
}
