package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.core.output.model.AuthorizationModel;

public interface ActionParams<A extends ActionParams.Args>
{
    String getCode();

    String getAction();

    A getArgs();

    void setAuthorizationPermission(String permission);

    AuthorizationModel getAuthorization();

    class Args
    {
    }
}
