package com.sd.eos.rpc.utils;

import android.support.annotation.CallSuper;
import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public abstract class DappWebSocketServer extends WebSocketServer
{
    private static final String TAG = DappWebSocketServer.class.getSimpleName();

    public static final String TAG_MSG = "42/scatter,";
    public static final String TAG_DATA_PAIR = "[\"pair\",";

    private boolean mIsStarted;

    public DappWebSocketServer()
    {
        super(new InetSocketAddress(50005));
    }

    public final boolean isStarted()
    {
        return mIsStarted;
    }

    @CallSuper
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        Log.i(TAG, "onOpen");
        conn.send("40/scatter");
    }

    @CallSuper
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        Log.i(TAG, "onClose:" + code + " " + reason + " " + remote);
    }

    @Override
    public final void onMessage(WebSocket conn, String message)
    {
        Log.i(TAG, "onMessage:" + message);
        if (message.startsWith(TAG_MSG))
        {
            final String data = message.substring(TAG_MSG.length());
            if (data.startsWith(TAG_DATA_PAIR))
            {
                final String json = data.substring(TAG_DATA_PAIR.length(), data.length() - 1);
                onDataPair(json);
            }
        }
    }

    @CallSuper
    @Override
    public void onError(WebSocket conn, Exception ex)
    {
        Log.e(TAG, "onError:" + ex);
        mIsStarted = false;
    }

    @CallSuper
    @Override
    public void onStart()
    {
        Log.i(TAG, "onStart");
        mIsStarted = true;
    }

    protected abstract void onDataPair(String json);
}
