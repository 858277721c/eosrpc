package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.core.output.model.AuthorizationModel;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseParams<A extends BaseParams.Args, B extends BaseParams.Builder> implements ActionParams<A>
{
    private String authorizationActor;
    private String authorizationPermission;

    protected BaseParams(B builder)
    {

    }

    public final String getAuthorizationActor()
    {
        return authorizationActor;
    }

    protected final void setAuthorizationActor(String actor)
    {
        this.authorizationActor = actor;
    }

    public final void setAuthorizationPermission(String permission)
    {
        this.authorizationPermission = permission;
    }

    @Override
    public final List<AuthorizationModel> getAuthorization()
    {
        final String authorizationActor = Utils.checkEmpty(this.authorizationActor, "authorization actor was not specified");
        final String authorizationPermission = Utils.checkEmpty(this.authorizationPermission, "authorization permission was not specified");

        final List<AuthorizationModel> list = new ArrayList<>();
        list.add(new AuthorizationModel(authorizationActor, authorizationPermission));

        return list;
    }

    protected static class Args<B> extends ActionParams.Args
    {
        protected Args(B builder)
        {
        }
    }

    protected static class Builder<T extends Builder>
    {

    }
}
