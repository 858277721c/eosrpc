package com.sd.lib.eos.rpc.action;

import com.sd.lib.eos.rpc.action.model.ActionModel;
import com.sd.lib.eos.rpc.utils.Utils;

public abstract class BaseAction<T> implements EosAction<T>
{
    @Override
    public final String toJson()
    {
        final String code = getCode();
        Utils.checkEmpty(code);

        final String action = getAction();
        Utils.checkEmpty(action);

        final T args = getArgs();
        Utils.checkNull(args);

        final ActionModel model = new ActionModel();
        model.setCode(code);
        model.setAction(action);
        model.setArgs(args);

        final String json = Utils.objectToJson(model);
        return json;
    }
}
