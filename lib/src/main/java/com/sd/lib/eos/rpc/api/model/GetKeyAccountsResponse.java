package com.sd.lib.eos.rpc.api.model;

import java.util.List;

public class GetKeyAccountsResponse
{
    private List<String> account_names;

    public String getAccountName(int index)
    {
        if (account_names == null || account_names.isEmpty())
            return null;

        if (index >= 0 && index < account_names.size())
            return account_names.get(index);

        return null;
    }

    public List<String> getAccount_names()
    {
        return account_names;
    }

    public void setAccount_names(List<String> account_names)
    {
        this.account_names = account_names;
    }
}
