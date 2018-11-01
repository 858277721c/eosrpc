package com.sd.lib.eos.rpc.api.model;

import java.util.List;

public class GetAccountResponse
{
    private String account_name; // 账号名称
    private long head_block_num;
    private String head_block_time;
    private boolean privileged;
    private String last_code_update;
    private String created;
    private String core_liquid_balance; // 余额
    private long ram_quota;  // 内存
    private long cpu_weight;
    private long net_weight;
    private Resource net_limit; // net资源
    private Resource cpu_limit; // cpu资源
    private long ram_usage;
    private List<Permission> permissions;
    private TotalResources total_resources;
    private SelfDelegatedBandwidth self_delegated_bandwidth;
    private RefundRequest refund_request;
    private VoterInfo voter_info;

    public String getAccount_name()
    {
        return account_name;
    }

    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
    }

    public long getHead_block_num()
    {
        return head_block_num;
    }

    public void setHead_block_num(long head_block_num)
    {
        this.head_block_num = head_block_num;
    }

    public String getHead_block_time()
    {
        return head_block_time;
    }

    public void setHead_block_time(String head_block_time)
    {
        this.head_block_time = head_block_time;
    }

    public boolean isPrivileged()
    {
        return privileged;
    }

    public void setPrivileged(boolean privileged)
    {
        this.privileged = privileged;
    }

    public String getLast_code_update()
    {
        return last_code_update;
    }

    public void setLast_code_update(String last_code_update)
    {
        this.last_code_update = last_code_update;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public String getCore_liquid_balance()
    {
        return core_liquid_balance;
    }

    public void setCore_liquid_balance(String core_liquid_balance)
    {
        this.core_liquid_balance = core_liquid_balance;
    }

    public long getRam_quota()
    {
        return ram_quota;
    }

    public void setRam_quota(long ram_quota)
    {
        this.ram_quota = ram_quota;
    }

    public long getCpu_weight()
    {
        return cpu_weight;
    }

    public void setCpu_weight(long cpu_weight)
    {
        this.cpu_weight = cpu_weight;
    }

    public long getNet_weight()
    {
        return net_weight;
    }

    public void setNet_weight(long net_weight)
    {
        this.net_weight = net_weight;
    }

    public Resource getNet_limit()
    {
        return net_limit;
    }

    public void setNet_limit(Resource net_limit)
    {
        this.net_limit = net_limit;
    }

    public Resource getCpu_limit()
    {
        return cpu_limit;
    }

    public void setCpu_limit(Resource cpu_limit)
    {
        this.cpu_limit = cpu_limit;
    }

    public long getRam_usage()
    {
        return ram_usage;
    }

    public void setRam_usage(long ram_usage)
    {
        this.ram_usage = ram_usage;
    }

    public List<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions)
    {
        this.permissions = permissions;
    }

    public TotalResources getTotal_resources()
    {
        return total_resources;
    }

    public void setTotal_resources(TotalResources total_resources)
    {
        this.total_resources = total_resources;
    }

    public SelfDelegatedBandwidth getSelf_delegated_bandwidth()
    {
        return self_delegated_bandwidth;
    }

    public void setSelf_delegated_bandwidth(SelfDelegatedBandwidth self_delegated_bandwidth)
    {
        this.self_delegated_bandwidth = self_delegated_bandwidth;
    }

    public RefundRequest getRefund_request()
    {
        return refund_request;
    }

    public void setRefund_request(RefundRequest refund_request)
    {
        this.refund_request = refund_request;
    }

    public VoterInfo getVoter_info()
    {
        return voter_info;
    }

    public void setVoter_info(VoterInfo voter_info)
    {
        this.voter_info = voter_info;
    }

    public static class Resource
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

    public static class Permission
    {
        private String perm_name;
        private String parent;
        private RequiredAuth required_auth;

        public String getPerm_name()
        {
            return perm_name;
        }

        public void setPerm_name(String perm_name)
        {
            this.perm_name = perm_name;
        }

        public String getParent()
        {
            return parent;
        }

        public void setParent(String parent)
        {
            this.parent = parent;
        }

        public RequiredAuth getRequired_auth()
        {
            return required_auth;
        }

        public void setRequired_auth(RequiredAuth required_auth)
        {
            this.required_auth = required_auth;
        }

        public static class RequiredAuth
        {
            private int threshold;
            private List<Key> key;

            public int getThreshold()
            {
                return threshold;
            }

            public void setThreshold(int threshold)
            {
                this.threshold = threshold;
            }

            public List<Key> getKey()
            {
                return key;
            }

            public void setKey(List<Key> key)
            {
                this.key = key;
            }

            public static class Key
            {
                private String key;
                private int weight;

                public String getKey()
                {
                    return key;
                }

                public void setKey(String key)
                {
                    this.key = key;
                }

                public int getWeight()
                {
                    return weight;
                }

                public void setWeight(int weight)
                {
                    this.weight = weight;
                }
            }
        }
    }

    public static class TotalResources
    {
        private String owner;
        private String net_weight;
        private String cpu_weight;
        private long ram_bytes;

        public String getOwner()
        {
            return owner;
        }

        public void setOwner(String owner)
        {
            this.owner = owner;
        }

        public String getNet_weight()
        {
            return net_weight;
        }

        public void setNet_weight(String net_weight)
        {
            this.net_weight = net_weight;
        }

        public String getCpu_weight()
        {
            return cpu_weight;
        }

        public void setCpu_weight(String cpu_weight)
        {
            this.cpu_weight = cpu_weight;
        }

        public long getRam_bytes()
        {
            return ram_bytes;
        }

        public void setRam_bytes(long ram_bytes)
        {
            this.ram_bytes = ram_bytes;
        }
    }

    public static class SelfDelegatedBandwidth
    {
        private String from;
        private String to;
        private String net_weight;
        private String cpu_weight;

        public String getFrom()
        {
            return from;
        }

        public void setFrom(String from)
        {
            this.from = from;
        }

        public String getTo()
        {
            return to;
        }

        public void setTo(String to)
        {
            this.to = to;
        }

        public String getNet_weight()
        {
            return net_weight;
        }

        public void setNet_weight(String net_weight)
        {
            this.net_weight = net_weight;
        }

        public String getCpu_weight()
        {
            return cpu_weight;
        }

        public void setCpu_weight(String cpu_weight)
        {
            this.cpu_weight = cpu_weight;
        }
    }

    public static class RefundRequest
    {
        private String owner;
        private String request_time;
        private String net_amount;
        private String cpu_amount;

        public String getOwner()
        {
            return owner;
        }

        public void setOwner(String owner)
        {
            this.owner = owner;
        }

        public String getRequest_time()
        {
            return request_time;
        }

        public void setRequest_time(String request_time)
        {
            this.request_time = request_time;
        }

        public String getNet_amount()
        {
            return net_amount;
        }

        public void setNet_amount(String net_amount)
        {
            this.net_amount = net_amount;
        }

        public String getCpu_amount()
        {
            return cpu_amount;
        }

        public void setCpu_amount(String cpu_amount)
        {
            this.cpu_amount = cpu_amount;
        }
    }

    public static class VoterInfo
    {
        private String owner;
        private String proxy;
        private long staked;
        private String last_vote_weight;
        private String proxied_vote_weight;
        private int is_proxy;

        public String getOwner()
        {
            return owner;
        }

        public void setOwner(String owner)
        {
            this.owner = owner;
        }

        public String getProxy()
        {
            return proxy;
        }

        public void setProxy(String proxy)
        {
            this.proxy = proxy;
        }

        public long getStaked()
        {
            return staked;
        }

        public void setStaked(long staked)
        {
            this.staked = staked;
        }

        public String getLast_vote_weight()
        {
            return last_vote_weight;
        }

        public void setLast_vote_weight(String last_vote_weight)
        {
            this.last_vote_weight = last_vote_weight;
        }

        public String getProxied_vote_weight()
        {
            return proxied_vote_weight;
        }

        public void setProxied_vote_weight(String proxied_vote_weight)
        {
            this.proxied_vote_weight = proxied_vote_weight;
        }

        public int getIs_proxy()
        {
            return is_proxy;
        }

        public void setIs_proxy(int is_proxy)
        {
            this.is_proxy = is_proxy;
        }
    }
}
