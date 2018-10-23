package com.sd.lib.eos.rpc.api.model;

public class ResourceModel
{
    // 已使用
    private long used;
    // 剩余
    private long available;
    // 总共
    private long max;

    public long getUsed()
    {
        return used;
    }

    public void setUsed(long used)
    {
        this.used = used;
    }

    public long getAvailable()
    {
        return available;
    }

    public void setAvailable(long available)
    {
        this.available = available;
    }

    public long getMax()
    {
        return max;
    }

    public void setMax(long max)
    {
        this.max = max;
    }
}
