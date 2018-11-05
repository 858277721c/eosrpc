package com.sd.lib.eos.rpc.api.model;

import com.sd.lib.eos.rpc.core.FEOSManager;

import java.util.List;

public class GetActionsResponse
{
    private long last_irreversible_block;
    private List<Action> actions;

    public long getLast_irreversible_block()
    {
        return last_irreversible_block;
    }

    public void setLast_irreversible_block(long last_irreversible_block)
    {
        this.last_irreversible_block = last_irreversible_block;
    }

    public List<Action> getActions()
    {
        return actions;
    }

    public void setActions(List<Action> actions)
    {
        this.actions = actions;
    }

    public static class Action
    {
        private int account_action_seq;
        private long block_num;
        private String block_time;
        private ActionTrace action_trace;

        public boolean hasInlineTraces()
        {
            final List<Action> list = action_trace.getInline_traces();
            return list != null && !list.isEmpty();
        }

        public int getAccount_action_seq()
        {
            return account_action_seq;
        }

        public void setAccount_action_seq(int account_action_seq)
        {
            this.account_action_seq = account_action_seq;
        }

        public long getBlock_num()
        {
            return block_num;
        }

        public void setBlock_num(long block_num)
        {
            this.block_num = block_num;
        }

        public String getBlock_time()
        {
            return block_time;
        }

        public void setBlock_time(String block_time)
        {
            this.block_time = block_time;
        }

        public ActionTrace getAction_trace()
        {
            return action_trace;
        }

        public void setAction_trace(ActionTrace action_trace)
        {
            this.action_trace = action_trace;
        }

        public static class ActionTrace
        {
            private String trx_id;
            private Receipt receipt;
            private Act act;
            private List<Action> inline_traces;

            public String getTrx_id()
            {
                return trx_id;
            }

            public void setTrx_id(String trx_id)
            {
                this.trx_id = trx_id;
            }

            public Receipt getReceipt()
            {
                return receipt;
            }

            public void setReceipt(Receipt receipt)
            {
                this.receipt = receipt;
            }

            public Act getAct()
            {
                return act;
            }

            public void setAct(Act act)
            {
                this.act = act;
            }

            public List<Action> getInline_traces()
            {
                return inline_traces;
            }

            public void setInline_traces(List<Action> inline_traces)
            {
                this.inline_traces = inline_traces;
            }

            public static class Receipt
            {
                private String receiver;
                private String act_digest;
                private long recv_sequence;
                private long code_sequence;
                private long abi_sequence;
                private List<List<String>> auth_sequence;

                public String getReceiver()
                {
                    return receiver;
                }

                public void setReceiver(String receiver)
                {
                    this.receiver = receiver;
                }

                public String getAct_digest()
                {
                    return act_digest;
                }

                public void setAct_digest(String act_digest)
                {
                    this.act_digest = act_digest;
                }

                public long getRecv_sequence()
                {
                    return recv_sequence;
                }

                public void setRecv_sequence(long recv_sequence)
                {
                    this.recv_sequence = recv_sequence;
                }

                public long getCode_sequence()
                {
                    return code_sequence;
                }

                public void setCode_sequence(long code_sequence)
                {
                    this.code_sequence = code_sequence;
                }

                public long getAbi_sequence()
                {
                    return abi_sequence;
                }

                public void setAbi_sequence(long abi_sequence)
                {
                    this.abi_sequence = abi_sequence;
                }

                public List<List<String>> getAuth_sequence()
                {
                    return auth_sequence;
                }

                public void setAuth_sequence(List<List<String>> auth_sequence)
                {
                    this.auth_sequence = auth_sequence;
                }
            }

            public static class Act
            {
                private String account;
                private String name;
                private List<Authorization> authorization;
                private String hex_data;
                private String data;

                public TransferData getTransferData()
                {
                    try
                    {
                        final TransferData transferData = FEOSManager.getInstance().getJsonConverter().jsonToObject(data, TransferData.class);
                        return transferData;
                    } catch (Exception e)
                    {
                        return null;
                    }
                }

                public String getAccount()
                {
                    return account;
                }

                public void setAccount(String account)
                {
                    this.account = account;
                }

                public String getName()
                {
                    return name;
                }

                public void setName(String name)
                {
                    this.name = name;
                }

                public List<Authorization> getAuthorization()
                {
                    return authorization;
                }

                public void setAuthorization(List<Authorization> authorization)
                {
                    this.authorization = authorization;
                }

                public String getHex_data()
                {
                    return hex_data;
                }

                public void setHex_data(String hex_data)
                {
                    this.hex_data = hex_data;
                }

                public String getData()
                {
                    return data;
                }

                public void setData(String data)
                {
                    this.data = data;
                }

                public static class Authorization
                {
                    private String actor;
                    private String permission;

                    public String getActor()
                    {
                        return actor;
                    }

                    public void setActor(String actor)
                    {
                        this.actor = actor;
                    }

                    public String getPermission()
                    {
                        return permission;
                    }

                    public void setPermission(String permission)
                    {
                        this.permission = permission;
                    }
                }

                public static class TransferData
                {
                    private String from;
                    private String to;
                    private String quantity;
                    private String memo;

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

                    public String getQuantity()
                    {
                        return quantity;
                    }

                    public void setQuantity(String quantity)
                    {
                        this.quantity = quantity;
                    }

                    public String getMemo()
                    {
                        return memo;
                    }

                    public void setMemo(String memo)
                    {
                        this.memo = memo;
                    }
                }
            }
        }
    }
}
