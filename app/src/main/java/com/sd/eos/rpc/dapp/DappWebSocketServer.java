package com.sd.eos.rpc.dapp;

import android.support.annotation.CallSuper;
import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

public abstract class DappWebSocketServer extends WebSocketServer
{
    private static final String TAG = DappWebSocketServer.class.getSimpleName();

    private static final String TAG_MSG = "42/scatter,";

    private static final String TAG_DATA_PREFIX = "[\"";
    private static final String TAG_DATA_SUFFIX = "\",";
    private static final String TAG_DATA_PAIR = TAG_DATA_PREFIX + DataType.Pair.getName() + TAG_DATA_SUFFIX;
    private static final String TAG_DATA_API = TAG_DATA_PREFIX + DataType.Api.getName() + TAG_DATA_SUFFIX;

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
                final String json = getDataFromMessage(TAG_DATA_PAIR, data);
                Log.i(TAG, "onDataPair:" + json);
                onDataPair(json, conn);
            } else if (data.startsWith(TAG_DATA_API))
            {
                final String json = getDataFromMessage(TAG_DATA_API, data);
                Log.i(TAG, "onDataApi:" + json);
                onDataApi(json, conn);
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

    private String getDataFromMessage(String tagData, String data)
    {
        return data.substring(tagData.length(), data.length() - 1);
    }

    /**
     * pair数据
     *
     * @param json
     * @param socket
     */
    private void onDataPair(String json, WebSocket socket)
    {
        responseSocket(json, DataType.Pair, socket);
    }

    /**
     * api数据
     *
     * @param json
     * @param socket
     */
    private void onDataApi(String json, WebSocket socket)
    {
        try
        {
            final JSONObject jsonObject = new JSONObject(json);
            final JSONObject jsonData = jsonObject.getJSONObject("data");
            final String type = jsonData.getString("type");

            final ApiType apiType = ApiType.from(type);
            if (apiType != null)
            {
                switch (apiType)
                {
                    case IdentityFromPermissions:
                        onApiTypeIdentityFromPermissions(jsonData, socket);
                        break;
                    case GetOrRequestIdentity:
                        onApiTypeGetOrRequestIdentity(jsonData, socket);
                        break;
                    default:
                        break;
                }
            } else
            {
                onDataError(new RuntimeException("unknow api type:" + type));
            }
        } catch (JSONException e)
        {
            onDataError(e);
        }
    }

    private void onApiTypeIdentityFromPermissions(JSONObject jsonObject, WebSocket socket)
    {
        try
        {
            final String id = jsonObject.getString("id");
            final JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", id);
            responseApi(jsonResponse.toString(), socket);
        } catch (Exception e)
        {
            onDataError(e);
        }
    }

    private void onApiTypeGetOrRequestIdentity(JSONObject jsonObject, WebSocket socket)
    {
        final String account = getEosAccount();
        if (account == null || account.isEmpty())
            throw new NullPointerException("account is empty");

        try
        {
            final JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", account);
            responseApi(jsonResponse.toString(), socket);
        } catch (JSONException e)
        {
            onDataError(e);
        }
    }

    protected abstract String getEosAccount();

    protected abstract void onDataError(Exception e);

    public final void responseApi(String json, WebSocket socket)
    {
        responseSocket(json, DataType.Api, socket);
    }

    private void responseSocket(String json, DataType dataType, WebSocket socket)
    {
        final StringBuilder sb = new StringBuilder(TAG_MSG);
        sb.append("[");
        sb.append("\"");
        sb.append(dataType.getName());
        if (DataType.Pair == dataType)
            sb.append("ed");
        sb.append("\"");

        sb.append(",");
        sb.append(json);
        sb.append("]");

        final String response = sb.toString();
        Log.i(TAG, "response:" + response);
        socket.send(response);
    }

    public enum DataType
    {
        Pair("pair"),
        Api("api");

        private final String name;

        DataType(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        @Override
        public String toString()
        {
            return getName();
        }
    }

    public enum ApiType
    {
        IdentityFromPermissions("identityFromPermissions"),
        GetOrRequestIdentity("getOrRequestIdentity");

        private final String name;

        ApiType(String name)
        {
            this.name = name;
        }

        public static ApiType from(String name)
        {
            if (name == null || name.isEmpty())
                return null;

            if (IdentityFromPermissions.getName().equals(name))
                return IdentityFromPermissions;
            else if (GetOrRequestIdentity.getName().equals(name))
                return GetOrRequestIdentity;

            return null;
        }

        public String getName()
        {
            return name;
        }

        @Override
        public String toString()
        {
            return getName();
        }
    }
}
