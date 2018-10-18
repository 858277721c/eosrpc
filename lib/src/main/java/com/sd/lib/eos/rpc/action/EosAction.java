package com.sd.lib.eos.rpc.action;

public interface EosAction<T>
{
    String getAction();

    String getCode();

    T getArgs();

    String toJson();
}
