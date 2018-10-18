package com.sd.lib.eos.rpc.action.eosio;

import com.sd.lib.eos.rpc.action.BaseAction;

public abstract class EosioAction<T> extends BaseAction<T>
{
    @Override
    public final String getCode()
    {
        return "eosio";
    }
}
