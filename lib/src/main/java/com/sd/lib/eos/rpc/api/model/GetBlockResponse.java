package com.sd.lib.eos.rpc.api.model;

import java.util.List;

public class GetBlockResponse
{
    private String timestamp;
    private String producer;
    private int confirmed;
    private String previous;
    private String transaction_mroot;
    private String action_mroot;
    private String block_mroot;
    private int schedule_version;
    private NewProducers new_producers;
    private String producer_signature;
    private List<Transaction> transactions;
    private String id;
    private long block_num;
    private long ref_block_prefix;

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getProducer()
    {
        return producer;
    }

    public void setProducer(String producer)
    {
        this.producer = producer;
    }

    public int getConfirmed()
    {
        return confirmed;
    }

    public void setConfirmed(int confirmed)
    {
        this.confirmed = confirmed;
    }

    public String getPrevious()
    {
        return previous;
    }

    public void setPrevious(String previous)
    {
        this.previous = previous;
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

    public int getSchedule_version()
    {
        return schedule_version;
    }

    public void setSchedule_version(int schedule_version)
    {
        this.schedule_version = schedule_version;
    }

    public NewProducers getNew_producers()
    {
        return new_producers;
    }

    public void setNew_producers(NewProducers new_producers)
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

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public long getBlock_num()
    {
        return block_num;
    }

    public void setBlock_num(long block_num)
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

    public static class NewProducers
    {
        private int version;
        private List<Producer> producers;

        public int getVersion()
        {
            return version;
        }

        public void setVersion(int version)
        {
            this.version = version;
        }

        public List<Producer> getProducers()
        {
            return producers;
        }

        public void setProducers(List<Producer> producers)
        {
            this.producers = producers;
        }

        public class Producer
        {
            private String producer_name;
            private String block_signing_key;

            public String getProducer_name()
            {
                return producer_name;
            }

            public void setProducer_name(String producer_name)
            {
                this.producer_name = producer_name;
            }

            public String getBlock_signing_key()
            {
                return block_signing_key;
            }

            public void setBlock_signing_key(String block_signing_key)
            {
                this.block_signing_key = block_signing_key;
            }
        }
    }

    public static class Transaction
    {
        private String status;
        private long cpu_usage_us;
        private long net_usage_words;
        private String trx;

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public long getCpu_usage_us()
        {
            return cpu_usage_us;
        }

        public void setCpu_usage_us(long cpu_usage_us)
        {
            this.cpu_usage_us = cpu_usage_us;
        }

        public long getNet_usage_words()
        {
            return net_usage_words;
        }

        public void setNet_usage_words(long net_usage_words)
        {
            this.net_usage_words = net_usage_words;
        }

        public String getTrx()
        {
            return trx;
        }

        public void setTrx(String trx)
        {
            this.trx = trx;
        }
    }
}
