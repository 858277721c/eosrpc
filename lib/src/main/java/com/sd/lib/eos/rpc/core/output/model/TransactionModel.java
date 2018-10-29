package com.sd.lib.eos.rpc.core.output.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionModel implements TransactionQuery
{
    private String expiration;
    private long ref_block_num;
    private long ref_block_prefix;
    private List<ActionModel> actions;

    public String getExpiration()
    {
        return expiration;
    }

    public void setExpiration(String expiration)
    {
        this.expiration = expiration;
    }

    public long getRef_block_num()
    {
        return ref_block_num;
    }

    public void setRef_block_num(long ref_block_num)
    {
        this.ref_block_num = ref_block_num;
    }

    public long getRef_block_prefix()
    {
        return ref_block_prefix;
    }

    public void setRef_block_prefix(long ref_block_prefix)
    {
        this.ref_block_prefix = ref_block_prefix;
    }

    public List<ActionModel> getActions()
    {
        if (actions == null)
            actions = new ArrayList<>(1);
        return actions;
    }

    public void setActions(List<ActionModel> actions)
    {
        this.actions = actions;
    }

    @Override
    public String queryExpiration()
    {
        return getExpiration();
    }

    @Override
    public long queryRef_block_num()
    {
        return getRef_block_num();
    }

    @Override
    public long queryRef_block_prefix()
    {
        return getRef_block_prefix();
    }

    @Override
    public List<ActionQuery> queryActions()
    {
        final List<ActionQuery> list = new ArrayList<>();

        final List<ActionModel> listModel = getActions();
        if (listModel != null)
        {
            for (ActionModel item : listModel)
            {
                list.add(item);
            }
        }
        return Collections.unmodifiableList(list);
    }
}
