package com.sd.lib.eos.rpc.output;

import java.util.List;

public interface ActionQuery
{
    String queryAccount();

    String queryName();

    List<AuthorizationQuery> queryAuthorization();

    String queryData();
}
