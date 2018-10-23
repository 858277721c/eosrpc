package com.sd.lib.eos.rpc.core.output.model;

import java.util.List;

public interface ActionQuery
{
    String queryAccount();

    String queryName();

    List<AuthorizationQuery> queryAuthorization();

    String queryData();
}
