package com.sd.lib.eos.rpc.api.model;

public class GetTableRowsResponse
{
    private String rows;
    private boolean more;

    public void setRows(String rows)
    {
        this.rows = rows;
    }

    public boolean isMore()
    {
        return more;
    }

    public void setMore(boolean more)
    {
        this.more = more;
    }

    public String getRows()
    {
        return rows;
    }
}
