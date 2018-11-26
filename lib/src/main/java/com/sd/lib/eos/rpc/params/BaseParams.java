package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.core.output.model.AuthorizationModel;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseParams<A extends BaseParams.Args, B extends BaseParams.Builder> implements ActionParams<A>
{
    private final List<AuthorizationModel> authorization;

    protected BaseParams(B builder)
    {
        final List<AuthorizationModel> authorization = builder.authorization;
        if (authorization == null || authorization.isEmpty())
            throw new RuntimeException("authorization was not specified");

        for (AuthorizationModel item : authorization)
        {
            Utils.checkNotNull(item, "authorization item is null");
            Utils.checkEmpty(item.getActor(), "authorization actor is empty");
            if (Utils.isEmpty(item.getPermission()))
                item.setPermission("active");
        }

        this.authorization = Collections.unmodifiableList(authorization);
    }

    @Override
    public final List<AuthorizationModel> getAuthorization()
    {
        return authorization;
    }

    protected static class Args<B> extends ActionParams.Args
    {
        protected Args(B builder)
        {
        }
    }

    protected static class Builder
    {
        protected List<AuthorizationModel> authorization;

        protected Builder setAuthorization(String actor)
        {
            setAuthorization(actor, null);
            return this;
        }

        protected Builder setAuthorization(String actor, String permission)
        {
            authorization = null;
            addAuthorization(actor, permission);
            return this;
        }

        private void addAuthorization(String actor, String permission)
        {
            final AuthorizationModel model = new AuthorizationModel();
            model.setActor(actor);
            model.setPermission(permission);

            if (authorization == null)
                authorization = new ArrayList<>();

            authorization.add(model);
        }
    }
}
