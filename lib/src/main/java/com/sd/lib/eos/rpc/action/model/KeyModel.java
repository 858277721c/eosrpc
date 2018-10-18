package com.sd.lib.eos.rpc.action.model;

public class KeyModel
{
    private String key;
    private int weight = 1;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }
}
