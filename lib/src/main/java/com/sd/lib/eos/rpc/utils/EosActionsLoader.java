package com.sd.lib.eos.rpc.utils;

import com.sd.lib.eos.rpc.api.model.GetActionsResponse;

import java.util.List;

public abstract class EosActionsLoader
{
    private final String mAccountName;

    public EosActionsLoader(String accountName)
    {
        mAccountName = accountName;
    }

    public final String getAccountName()
    {
        return mAccountName;
    }

    protected abstract String getLogTag();

    public abstract void reset();

    public abstract boolean hasNextPage();

    public abstract List<GetActionsResponse.Action> loadPage(int pageSize) throws Exception;
}
