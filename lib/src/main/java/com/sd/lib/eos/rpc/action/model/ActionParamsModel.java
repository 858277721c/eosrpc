package com.sd.lib.eos.rpc.action.model;

public class ActionParamsModel
{
    private String code;
    private String action;
    private Object args;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public Object getArgs()
    {
        return args;
    }

    public void setArgs(Object args)
    {
        this.args = args;
    }
}
