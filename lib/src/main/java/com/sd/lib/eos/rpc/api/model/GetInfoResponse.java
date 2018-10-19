package com.sd.lib.eos.rpc.api.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetInfoResponse
{
    private String server_version;
    private String chain_id;
    private long head_block_num;
    private long last_irreversible_block_num;
    private String head_block_id;
    private String head_block_time;
    private String head_block_producer;

    public String getHeadBlockTimeAfter(int mille)
    {
        try
        {
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            final Date date = format.parse(this.head_block_time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MILLISECOND, mille);

            return format.format(calendar.getTime());
        } catch (ParseException e)
        {
            e.printStackTrace();
            return this.head_block_time;
        }
    }

    public String getServer_version()
    {
        return server_version;
    }

    public void setServer_version(String server_version)
    {
        this.server_version = server_version;
    }

    public String getChain_id()
    {
        return chain_id;
    }

    public void setChain_id(String chain_id)
    {
        this.chain_id = chain_id;
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
