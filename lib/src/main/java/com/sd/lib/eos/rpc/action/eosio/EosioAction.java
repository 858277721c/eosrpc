package com.sd.lib.eos.rpc.action.eosio;

import android.text.TextUtils;

import com.sd.lib.eos.rpc.action.EosAction;

public abstract class EosioAction implements EosAction
{
    private final String mAction;

    protected EosioAction(String action)
    {
        if (TextUtils.isEmpty(action))
            throw new NullPointerException();
        mAction = action;
    }

    @Override
    public final String getAction()
    {
        return mAction;
    }

    @Override
    public final String getCode()
    {
        return "eosio";
    }
}
