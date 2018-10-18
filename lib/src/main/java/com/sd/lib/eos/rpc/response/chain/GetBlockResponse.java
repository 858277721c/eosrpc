package com.sd.lib.eos.rpc.response.chain;

public class GetBlockResponse
{
    private String previous;
    private String timestamp;
    private String transaction_mroot;
    private String action_mroot;
    private String block_mroot;
    private String producer;
    private int schedule_version;
    private NewProducersModel new_producers;
    private String producer_signature;
    private String id;
    private int block_num;
    private long ref_block_prefix;

    public String getPrevious()
    {
        return previous;
    }

    public void setPrevious(String previous)
    {
        this.previous = previous;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getTransaction_mroot()
    {
        return transaction_mroot;
    }

    public void setTransaction_mroot(String transaction_mroot)
    {
        this.transaction_mroot = transaction_mroot;
    }

    public String getAction_mroot()
    {
        return action_mroot;
    }

    public void setAction_mroot(String action_mroot)
    {
        this.action_mroot = action_mroot;
    }

    public String getBlock_mroot()
    {
        return block_mroot;
    }

    public void setBlock_mroot(String block_mroot)
    {
        this.block_mroot = block_mroot;
    }

    public String getProducer()
    {
        return producer;
    }

    public void setProducer(String producer)
    {
        this.producer = producer;
    }

    public int getSchedule_version()
    {
        return schedule_version;
    }

    public void setSchedule_version(int schedule_version)
    {
        this.schedule_version = schedule_version;
    }

    public NewProducersModel getNew_producers()
    {
        return new_producers;
    }

    public void setNew_producers(NewProducersModel new_producers)
    {
        this.new_producers = new_producers;
    }

    public String getProducer_signature()
    {
        return producer_signature;
    }

    public void setProducer_signature(String producer_signature)
    {
        this.producer_signature = producer_signature;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getBlock_num()
    {
        return block_num;
    }

    public void setBlock_num(int block_num)
    {
        this.block_num = block_num;
    }

    public long getRef_block_prefix()
    {
        return ref_block_prefix;
    }

    public void setRef_block_prefix(long ref_block_prefix)
    {
        this.ref_block_prefix = ref_block_prefix;
    }
}
