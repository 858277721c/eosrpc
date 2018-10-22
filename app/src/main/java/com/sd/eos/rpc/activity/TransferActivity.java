package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sd.eos.rpc.R;
import com.sd.lib.utils.context.FToast;

/**
 * 转账
 */
public class TransferActivity extends BaseActivity implements View.OnClickListener
{
    private EditText et_from, et_to;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transfer);
        et_from = findViewById(R.id.et_from);
        et_to = findViewById(R.id.et_to);

        findViewById(R.id.btn_transfer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_transfer:

                final String from = et_from.getText().toString();
                if (TextUtils.isEmpty(from))
                {
                    FToast.show("请输入转账账号");
                    return;
                }

                final String to = et_to.getText().toString();
                if (TextUtils.isEmpty(from))
                {
                    FToast.show("请输入收款账号");
                    return;
                }

                break;
        }
    }
}
