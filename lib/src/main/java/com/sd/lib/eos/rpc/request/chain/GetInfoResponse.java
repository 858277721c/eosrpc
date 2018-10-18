package com.sd.lib.eos.rpc.request.chain;

public class GetInfoResponse
{
    private String server_version;
    private long head_block_num;
    private long last_irreversible_block_num;
    private String head_block_id;
    private String head_block_time;
    private String head_block_producer;

    public String getServer_version()
    {
        return server_version;
    }

    public void setServer_version(String server_version)
    {
        this.server_version = server_version;
    }

    public long getHead_block_num()
    {
        return head_block_num;
    }

    public void setHead_block_num(long head_block_num)
    {
        this.head_block_num = head_block_num;
    }

    public long getLast_irreversible_block_num()
    {
        return last_irreversible_block_num;
    }

    public void setLast_irreversible_block_num(long last_irreversible_block_num)
    {
        this.last_irreversible_block_num = last_irreversible_block_num;
    }

    public String getHead_block_id()
    {
        return head_block_id;
    }

    public void setHead_block_id(String head_block_id)
    {
        this.head_block_id = head_block_id;
    }

    public String getHead_block_time()
    {
        return head_block_time;
    }

    public void setHead_block_time(String head_block_time)
    {
        this.head_block_time = head_block_time;
    }

    public String getHead_block_producer()
    {
        return head_block_producer;
    }

    public void setHead_block_producer(String head_block_producer)
    {
        this.head_block_producer = head_block_producer;
    }
}
