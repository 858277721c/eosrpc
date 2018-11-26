package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.core.output.model.AuthorizationModel;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseParams<A extends BaseParams.Args, B extends BaseParams.Builder> implements ActionParams<A>
{
    private final List<AuthorizationModel> authorization;

    protected BaseParams(B builder)
    {
        final String authorizationActor = Utils.checkEmpty(builder.authorizationActor, "authorization actor was not specified");
        final String authorizationPermission = Utils.checkEmpty(builder.authorizationPermission, "authorization permission was not specified");

        final List<AuthorizationModel> list = new ArrayList<>();
        list.add(new AuthorizationModel(authorizationActor, authorizationPermission));

        this.authorization = Collections.unmodifiableList(list);
    }

    @Override
    public final List<AuthorizationModel> getAuthorization()
    {
        return authorization;
    }

    protected static class Args<B> extends ActionParams.Args
    {
        protected Args(B builder)
        {
        }
    }

    protected static class Builder
    {
        protected String authorizationActor;
        protected String authorizationPermission;

        protected Builder setAuthorization(String actor)
        {
            setAuthorization(actor, "active");
            return this;
        }

        protected Builder setAuthorization(String actor, String permission)
        {
            this.authorizationActor = actor;
            this.authorizationPermission = permission;
            return this;
        }

        public Builder setAuthorizationPermission(String permission)
        {
            this.authorizationPermission = permission;
            return this;
        }
    }
}
