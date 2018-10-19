package com.sd.lib.eos.rpc.output.model;

import com.sd.lib.eos.rpc.output.AuthorizationQuery;

public class AuthorizationModel implements AuthorizationQuery
{
    private String actor;
    private String permission;

    public String getActor()
    {
        return actor;
    }

    public void setActor(String actor)
    {
        this.actor = actor;
    }

    public String getPermission()
    {
        return permission;
    }

    public void setPermission(String permission)
    {
        this.permission = permission;
    }

    @Override
    public String queryActor()
    {
        return getActor();
    }

    @Override
    public String queryPermission()
    {
        return getPermission();
    }
}
