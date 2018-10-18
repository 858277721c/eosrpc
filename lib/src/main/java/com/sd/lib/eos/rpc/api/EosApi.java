package com.sd.lib.eos.rpc.api;

import com.sd.lib.eos.rpc.http.EosRequestCallback;
import com.sd.lib.http.Request;
import com.sd.lib.http.RequestHandler;
import com.sd.lib.http.impl.httprequest.PostRequest;

public abstract class EosApi<T>
{
    private RequestHandler mRequestHandler;

    protected abstract void initRequest(Request request);

    public final void execute(EosRequestCallback<T> callback)
    {
        final Request request = new PostRequest();
        initRequest(request);
        mRequestHandler = request.execute(callback);
    }

    public void cancel()
    {
        if (mRequestHandler != null)
        {
            mRequestHandler.cancel();
            mRequestHandler = null;
        }
    }
}
