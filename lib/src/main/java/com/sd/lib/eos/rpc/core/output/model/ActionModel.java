package com.sd.lib.eos.rpc.core.output.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionModel implements ActionQuery
{
    private String account;
    private String name;
    private List<AuthorizationModel> authorization;
    private String data;

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

    public List<AuthorizationModel> getAuthorization()
    {
        return authorization;
    }

    public void setAuthorization(List<AuthorizationModel> authorization)
    {
        this.authorization = authorization;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    @Override
    public String queryAccount()
    {
        return getAccount();
    }

    @Override
    public String queryName()
    {
        return getName();
    }

    @Override
    public List<AuthorizationQuery> queryAuthorization()
    {
        final List<AuthorizationQuery> list = new ArrayList<>();

        final List<AuthorizationModel> listModel = getAuthorization();
        if (list != null)
        {
            for (AuthorizationModel item : listModel)
            {
                list.add(item);
            }
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public String queryData()
    {
        return getData();
    }
}
