package com.sd.eos.rpc.dapp;

import android.support.annotation.CallSuper;
import android.util.Log;

import com.sd.eos.rpc.dapp.model.EosAccountModel;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
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
        Log.i(TAG, "----->:" + message);
        if (message.startsWith(TAG_MSG))
        {
            final String data = message.substring(TAG_MSG.length());
            if (data.startsWith(TAG_DATA_PAIR))
            {
                final String json = getDataFromMessage(TAG_DATA_PAIR, data);
                onDataPair(json, conn);
            } else if (data.startsWith(TAG_DATA_API))
            {
                final String json = getDataFromMessage(TAG_DATA_API, data);
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
                final String id = jsonData.optString("id");
                if (id != null || !id.isEmpty())
                {
                    switch (apiType)
                    {
                        case IdentityFromPermissions:
                            onApiTypeIdentityFromPermissions(jsonData, id, socket);
                            break;
                        case GetOrRequestIdentity:
                            onApiTypeGetOrRequestIdentity(jsonData, id, socket);
                            break;
                        default:
                            break;
                    }
                } else
                {
                    onDataError(new RuntimeException("api id was not found in json:" + json));
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

    private void onApiTypeIdentityFromPermissions(JSONObject jsonData, String id, WebSocket socket)
    {
        try
        {
            new ApiResponser(id, socket).send(id);
        } catch (JSONException e)
        {
            onDataError(new RuntimeException("identityFromPermissions response error:" + e));
        }
    }

    private void onApiTypeGetOrRequestIdentity(JSONObject jsonData, String id, WebSocket socket)
    {
        final EosAccountModel account = getEosAccount();
        if (account == null)
            throw new NullPointerException("account is null");

        final String blockchain = account.getBlockchain();
        final String name = account.getName();
        final String authority = account.getAuthority();
        final String publicKey = account.getPublicKey();

        try
        {
            final JSONObject jsonAccount = new JSONObject();
            jsonAccount.put("blockchain", blockchain);
            jsonAccount.put("name", name);
            jsonAccount.put("authority", authority);
            jsonAccount.put("publicKey", publicKey);

            final JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonAccount);

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("accounts", jsonArray);

            new ApiResponser(id, socket).send(jsonObject);
        } catch (JSONException e)
        {
            onDataError(new RuntimeException("getOrRequestIdentity response error:" + e));
        }
    }

    protected abstract EosAccountModel getEosAccount();

    protected abstract void onDataError(Exception e);

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
        Log.i(TAG, "<-----:" + response);
        socket.send(response);
    }

    private final class ApiResponser
    {
        private final String mId;
        private final WebSocket mSocket;

        public ApiResponser(String id, WebSocket socket)
        {
            mId = id;
            mSocket = socket;
        }

        private JSONObject newData() throws JSONException
        {
            final JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", mId);
            return jsonResponse;
        }

        public void send(Object object) throws JSONException
        {
            final JSONObject jsonData = newData();
            jsonData.put("result", object);

            responseSocket(jsonData.toString(), DataType.Api, mSocket);
        }
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
