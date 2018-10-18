package com.sd.lib.eos.rpc.action.params;

import com.sd.lib.eos.rpc.action.model.PermissionModel;

/**
 * 创建新账户
 */
public class NewaccountActionParams extends BaseParams<NewaccountActionParams.Args>
{
    private final Args args;

    private NewaccountActionParams(Builder builder)
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
         * 设置创建者账户
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
         * 设置新账户名称
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

        public NewaccountActionParams build()
        {
            return new NewaccountActionParams(this);
        }
    }
}
