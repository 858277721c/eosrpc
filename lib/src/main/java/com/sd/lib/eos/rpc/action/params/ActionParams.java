package com.sd.lib.eos.rpc.action.params;

public interface ActionParams<T>
{
    String getAction();

    String getCode();

    T getArgs();

    String toJson();
}
