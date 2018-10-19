package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.output.model.AuthorizationModel;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseParams<A extends ActionParams.Args, B extends BaseParams.Builder> implements ActionParams<A>
{
    private final List<AuthorizationModel> authorization;

    protected BaseParams(B builder)
    {
        this.authorization = Collections.unmodifiableList(builder.authorization);
    }

    @Override
    public String getCode()
    {
        return "eosio";
    }

    @Override
    public final List<AuthorizationModel> getAuthorization()
    {
        return authorization;
    }

    protected static class Builder<B extends Builder>
    {
        protected List<AuthorizationModel> authorization;

        public B addAuthorization(AuthorizationModel model)
        {
            Utils.checkNotNull(model, "");

            if (authorization == null)
                authorization = new ArrayList<>();

            authorization.add(model);
            return (B) this;
        }
    }
}
