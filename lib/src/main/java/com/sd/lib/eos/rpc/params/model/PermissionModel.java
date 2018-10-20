package com.sd.lib.eos.rpc.params.model;

import java.util.Arrays;
import java.util.List;

public class PermissionModel
{
    private final int threshold;
    private final List<KeyModel> keys;

    private final String[] accounts;
    private final String[] waits;

    public PermissionModel(int threshold, KeyModel... keys)
    {
        this.threshold = threshold;
        this.keys = Arrays.asList(keys);

        this.accounts = new String[]{};
        this.waits = new String[]{};
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
