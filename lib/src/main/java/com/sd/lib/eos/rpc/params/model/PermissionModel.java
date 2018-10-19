package com.sd.lib.eos.rpc.params.model;

import java.util.Arrays;
import java.util.List;

public class PermissionModel
{
    private final int threshold;
    private final List<KeyModel> keys;

    public PermissionModel(int threshold, KeyModel... keys)
    {
        this.threshold = threshold;
        this.keys = Arrays.asList(keys);
    }

    public static PermissionModel create(String publicKey)
    {
       return new PermissionModel(1, new KeyModel(publicKey, 1));
    }

    public int getThreshold()
    {
        return threshold;
    }

    public List<KeyModel> getKeys()
    {
        return keys;
    }
}
