package com.sd.lib.eos.rpc.core.output.model;

import java.util.List;

public interface TransactionQuery
{
    String queryExpiration();

    long queryRef_block_num();

    long queryRef_block_prefix();

    List<ActionQuery> queryActions();
}
