package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sd.eos.rpc.R;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetCurrencyBalanceResponse;
import com.sd.lib.task.FTask;

/**
 * 查询账号余额
 */
public class GetCurrencyActivity extends BaseActivity
{
    private TextView tv_content;
    private EditText et_get_currency;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_get_currency);
        tv_content = findViewById(R.id.tv_content);
        et_get_currency = findViewById(R.id.et_get_currency);

        findViewById(R.id.btn_get_currency).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String account = et_get_currency.getText().toString();
                requestData(account);
            }
        });
    }

    private void requestData(final String account)
    {
        tv_content.setText("");
        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final ApiResponse<GetCurrencyBalanceResponse> response = new RpcApi().getCurrencyBalance(account);

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
