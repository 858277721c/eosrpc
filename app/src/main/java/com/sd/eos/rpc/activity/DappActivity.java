package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.util.Log;

import com.sd.eos.rpc.R;
import com.sd.eos.rpc.utils.DappWebSocketServer;
import com.sd.lib.webview.FWebView;

public class DappActivity extends BaseActivity
{
    public static final String TAG = DappActivity.class.getSimpleName();

    private FWebView mWebView;

    private DappWebSocketServer mWebSocketServer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dapp);
        mWebView = findViewById(R.id.webview);

        getWebSocketServer().start();
    }

    private DappWebSocketServer getWebSocketServer()
    {
        if (mWebSocketServer == null)
        {
            mWebSocketServer = new DappWebSocketServer()
            {
                @Override
                protected void onDataPair(String json)
                {
                    Log.i(TAG, "onDataPair:" + json);
                }

                @Override
                public void onStart()
                {
                    super.onStart();
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mWebView.get("https://bancor3d.com?ref=welovecasino");
                        }
                    });
                }
            };
        }
        return mWebSocketServer;
    }
}
