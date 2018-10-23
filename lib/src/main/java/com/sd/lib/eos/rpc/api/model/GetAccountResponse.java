package com.sd.lib.eos.rpc.api.model;

public class GetAccountResponse
{
    // 账号名称
    private String account_name;
    // 余额
    private String core_liquid_balance;
    // cpu资源
    private ResourceModel cpu_limit;
    // net资源
    private ResourceModel net_limit;

    public String getAccount_name()
    {
        return account_name;
    }

    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
    }

    public String getCore_liquid_balance()
    {
        return core_liquid_balance;
    }

    public void setCore_liquid_balance(String core_liquid_balance)
    {
        this.core_liquid_balance = core_liquid_balance;
    }

    public ResourceModel getCpu_limit()
    {
        return cpu_limit;
    }

    public void setCpu_limit(ResourceModel cpu_limit)
    {
        this.cpu_limit = cpu_limit;
    }

    public ResourceModel getNet_limit()
    {
        return net_limit;
    }

    public void setNet_limit(ResourceModel net_limit)
    {
        this.net_limit = net_limit;
    }
}
