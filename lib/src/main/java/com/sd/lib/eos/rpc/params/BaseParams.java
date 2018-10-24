package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.core.output.model.AuthorizationModel;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseParams<A extends ActionParams.Args, B extends BaseParams.Builder> implements ActionParams<A>
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

    protected static class Builder<B extends Builder>
    {
        protected List<AuthorizationModel> authorization;

        public B setAuthorization(String actor)
        {
            return setAuthorization(actor, null);
        }

        public B setAuthorization(String actor, String permission)
        {
            authorization = null;
            return addAuthorization(actor, permission);
        }

        private B addAuthorization(String actor, String permission)
        {
            final AuthorizationModel model = new AuthorizationModel();
            model.setActor(actor);
            model.setPermission(permission);

            if (authorization == null)
                authorization = new ArrayList<>();

            authorization.add(model);
            return (B) this;
        }
    }
}
