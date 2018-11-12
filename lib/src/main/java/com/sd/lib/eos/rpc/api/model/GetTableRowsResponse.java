package com.sd.lib.eos.rpc.api.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetTableRowsResponse
{
    private String rows;
    private boolean more;

    /**
     * 把查询记录转为可赎回的记录
     *
     * @return
     */
    public List<DelbandRow> getDelbandRows()
    {
        final List<DelbandRow> list = new ArrayList<>();
        try
        {
            final JSONArray jsonArray = new JSONArray(rows);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                final JSONObject jsonObject = jsonArray.getJSONObject(i);

                final DelbandRow delbandRow = new DelbandRow();
                delbandRow.setFrom(jsonObject.getString("from"));
                delbandRow.setTo(jsonObject.getString("to"));
                delbandRow.setNet_weight(jsonObject.getString("net_weight"));
                delbandRow.setCpu_weight(jsonObject.getString("cpu_weight"));
                list.add(delbandRow);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }

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

    public static class DelbandRow
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
}
