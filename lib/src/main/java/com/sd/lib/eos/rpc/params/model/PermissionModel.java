package com.sd.lib.eos.rpc.params.model;

import com.sd.lib.eos.rpc.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class PermissionModel
{
    private final int threshold;
    private final List<KeyModel> keys;

    private final String[] accounts;
    private final String[] waits;

    public PermissionModel(int threshold, KeyModel keyModel)
    {
        this.threshold = threshold;
        this.keys = Arrays.asList(new KeyModel[]{keyModel});

        this.accounts = new String[]{};
        this.waits = new String[]{};
    }

    public static PermissionModel create(String publicKey)
    {
        return new PermissionModel(1, new KeyModel(publicKey, 1));
    }

    public boolean hasKey()
    {
        if (keys == null || keys.isEmpty())
            return false;

        for (KeyModel item : keys)
        {
            if (Utils.isEmpty(item.getKey()))
                return false;
        }
        return true;
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
