package com.sd.eos.rpc.model;

import com.sd.lib.cache.FCache;

import java.util.ArrayList;
import java.util.List;

public class AccountHolder
{
    private List<AccountModel> accounts;

    private AccountHolder()
    {
        accounts = new ArrayList<>();
    }

    public static AccountHolder get()
    {
        AccountHolder holder = FCache.disk().cacheObject().get(AccountHolder.class);
        if (holder == null)
        {
            holder = new AccountHolder();
            holder.save();
        }
        return holder;
    }

    public void add(AccountModel model)
    {
        if (accounts.contains(model))
            return;

        accounts.add(model);
        save();
    }

    public List<AccountModel> getAllAccount()
    {
        return new ArrayList<>(accounts);
    }

    private void save()
    {
        FCache.disk().cacheObject().put(this);
    }
}
