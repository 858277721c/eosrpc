package com.sd.eos.rpc.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.sd.eos.rpc.R;
import com.sd.eos.rpc.dapp.DappWebSocketServer;
import com.sd.lib.webview.FWebView;
import com.sd.lib.webview.client.FWebViewClient;

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

        mWebView.setWebViewClient(new FWebViewClient(this)
        {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                handler.proceed();
            }
        });

        getWebSocketServer().start();
    }

    private DappWebSocketServer getWebSocketServer()
    {
        if (mWebSocketServer == null)
        {
            mWebSocketServer = new DappWebSocketServer()
            {
                @Override
                public void onStart()
                {
                    super.onStart();
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mWebView.get("https://betdice.one/?ref=welovecasino");
                        }
                    });
                }

                @Override
                protected String getEosAccount()
                {
                    return "zjunzjun1234";
                }

                @Override
                protected void onDataError(Exception e)
                {
                    Log.e(TAG, "onDataError:" + e);
                }
            };
        }
        return mWebSocketServer;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }
}
