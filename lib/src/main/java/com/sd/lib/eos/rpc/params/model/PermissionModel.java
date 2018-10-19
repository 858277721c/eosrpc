package com.sd.lib.eos.rpc.params.model;

public class PermissionModel
{
    private int threshold;
    private KeyModel keys;

    public int getThreshold()
    {
        return threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }

    public KeyModel getKeys()
    {
        return keys;
    }

    public void setKeys(KeyModel keys)
    {
        this.keys = keys;
    }
}
