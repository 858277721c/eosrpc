package com.sd.lib.eos.rpc.action.eosio;

import com.sd.lib.eos.rpc.action.model.PermissionModel;

/**
 * 创建新账号
 */
public class NewaccountAction extends EosioAction<NewaccountAction.Args>
{
    private final Args args;

    private NewaccountAction(Builder builder)
    {
        this.args = new Args(builder);
    }

    @Override
    public final String getAction()
    {
        return "newaccount";
    }

    @Override
    public Args getArgs()
    {
        return this.args;
    }

    public static class Args
    {
        private final String creator;
        private final String name;
        private final PermissionModel owner;
        private final PermissionModel active;

        private Args(Builder builder)
        {
            this.creator = builder.creator;
            this.name = builder.name;
            this.owner = builder.owner;
            this.active = builder.active;
        }

        public String getCreator()
        {
            return creator;
        }

        public String getName()
        {
            return name;
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

    public static class Builder
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
