package com.sd.lib.eos.rpc.request.chain;

import java.util.List;

public class NewProducersModel
{
    private int version;
    private List<ProducerModel> producers;

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public List<ProducerModel> getProducers()
    {
        return producers;
    }

    public void setProducers(List<ProducerModel> producers)
    {
        this.producers = producers;
    }
}
