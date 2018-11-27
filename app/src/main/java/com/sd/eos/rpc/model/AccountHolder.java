package com.sd.eos.rpc.model;

import android.text.TextUtils;

import com.sd.lib.cache.FCache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountHolder
{
    private Map<String, AccountModel> mapAccount = new LinkedHashMap<>();

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

    public void add(AccountModel model)
    {
        if (model == null
                || TextUtils.isEmpty(model.getAccount())
                || TextUtils.isEmpty(model.getPrivateKey())
                || TextUtils.isEmpty(model.getPublicKey()))
        {
            throw new NullPointerException();
        }

        mapAccount.put(model.getAccount(), model);
        save();
    }

    public List<AccountModel> getAllAccount()
    {
        return new ArrayList<>(mapAccount.values());
    }
}
