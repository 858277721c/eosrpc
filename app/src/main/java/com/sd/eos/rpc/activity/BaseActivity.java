package com.sd.eos.rpc.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity
{
    private ProgressDialog mProgressDialog;

    protected final void showProgress(CharSequence message)
    {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected final void dismissProgress()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected final void setTextContent(final TextView textView, final CharSequence text)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                textView.setText(text);
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dismissProgress();
    }
}
