package com.sd.lib.eos.rpc.action.params;

import com.sd.lib.eos.rpc.action.model.ActionParamsModel;
import com.sd.lib.eos.rpc.utils.Utils;

public abstract class BaseParams<T> implements ActionParams<T>
{
    @Override
    public final String getCode()
    {
        return "eosio";
    }

    @Override
    public final String toJson()
    {
        final String code = getCode();
        Utils.checkEmpty(code);

        final String action = getAction();
        Utils.checkEmpty(action);

        final T args = getArgs();
        Utils.checkNull(args);

        final ActionParamsModel model = new ActionParamsModel();
        model.setCode(code);
        model.setAction(action);
        model.setArgs(args);

        final String json = Utils.objectToJson(model);
        return json;
    }
}
