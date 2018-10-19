package com.sd.lib.eos.rpc.params.model;

public class KeyModel
{
    private final String key;
    private final int weight;

    public KeyModel(String key, int weight)
    {
        this.key = key;
        this.weight = weight;
    }

    public String getKey()
    {
        return key;
    }

    public int getWeight()
    {
        return weight;
    }
}
