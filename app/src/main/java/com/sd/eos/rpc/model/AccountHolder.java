package com.sd.eos.rpc.model;

import com.sd.lib.cache.FCache;

public class AccountHolder
{
    private AccountHolder()
    {
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

    private void save()
    {
        FCache.disk().cacheObject().put(this);
    }
}
