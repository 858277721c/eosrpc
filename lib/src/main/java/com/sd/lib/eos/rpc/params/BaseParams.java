package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.core.output.model.AuthorizationModel;

public abstract class BaseParams<A extends BaseParams.Args, B extends BaseParams.Builder> implements ActionParams<A>
{
    private String authorizationActor;
    private String authorizationPermission;

    protected BaseParams(B builder)
    {

    }

    protected final void setAuthorizationActor(String actor)
    {
        this.authorizationActor = actor;
    }

    @Override
    public final void setAuthorizationPermission(String permission)
    {
        this.authorizationPermission = permission;
    }

    @Override
    public final AuthorizationModel getAuthorization()
    {
        return new AuthorizationModel(authorizationActor, authorizationPermission);
    }

    public static class Args<B> extends ActionParams.Args
    {
        protected Args(B builder)
        {
        }
    }

    public static class Builder<T extends Builder>
    {

    }
}
