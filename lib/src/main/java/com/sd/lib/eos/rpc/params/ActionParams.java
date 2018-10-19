package com.sd.lib.eos.rpc.params;

import com.sd.lib.eos.rpc.common.Jsonable;
import com.sd.lib.eos.rpc.output.model.AuthorizationModel;
import com.sd.lib.eos.rpc.utils.Utils;

import java.util.List;

public interface ActionParams<A extends ActionParams.Args>
{
    String getAction();

    String getCode();

    A getArgs();

    List<AuthorizationModel> getAuthorization();

    class Args implements Jsonable
    {
        @Override
        public String toJson()
        {
            return Utils.objectToJson(this);
        }
    }
}
