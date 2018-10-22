package com.sd.eos.rpc.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dismissProgress();
    }
}
