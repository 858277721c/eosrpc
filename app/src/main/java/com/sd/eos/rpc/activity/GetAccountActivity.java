package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sd.eos.rpc.R;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.task.FTask;

/**
 * 查询账号信息
 */
public class GetAccountActivity extends BaseActivity
{
    private TextView tv_content;
    private EditText et_get_account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_get_account);
        tv_content = findViewById(R.id.tv_content);
        et_get_account = findViewById(R.id.et_get_account);

        findViewById(R.id.btn_get_account).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String account = et_get_account.getText().toString();
                getAccount(account);
            }
        });
    }

    private void getAccount(final String account)
    {
        tv_content.setText("");
        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final ApiResponse<GetAccountResponse> response = new RpcApi().getAccount(account);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (response.isSuccessful())
                            tv_content.setText(new Gson().toJson(response.getSuccess()));
                        else
                            tv_content.setText(new Gson().toJson(response.getError()));
                    }
                });
            }
        }.submit();
    }
}
