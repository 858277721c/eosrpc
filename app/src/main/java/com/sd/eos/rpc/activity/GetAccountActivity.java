package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sd.eos.rpc.R;
import com.sd.eos.rpc.dialog.LocalAccountDialog;
import com.sd.eos.rpc.model.AccountModel;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.task.FTask;

/**
 * 查询账号信息
 */
public class GetAccountActivity extends BaseActivity  implements View.OnClickListener
{
    private TextView tv_content;
    private EditText et_account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_get_account);
        tv_content = findViewById(R.id.tv_content);
        et_account = findViewById(R.id.et_account);

        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.tv_account_label).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_account_label:
                final LocalAccountDialog dialog = new LocalAccountDialog(this);
                dialog.setCallback(new LocalAccountDialog.Callback()
                {
                    @Override
                    public void onClickItem(AccountModel model)
                    {
                        et_account.setText(model.getAccount());
                    }
                });
                dialog.show();
                break;
            case R.id.btn_query:
                getAccount(et_account.getText().toString());
                break;
        }
    }

    private void getAccount(final String account)
    {
        setTextContent(tv_content, "");
        showProgress("");
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
                            setTextContent(tv_content, new Gson().toJson(response.getSuccess()));
                        else
                            setTextContent(tv_content, new Gson().toJson(response.getError()));
                    }
                });
            }

            @Override
            protected void onError(Exception e)
            {
                super.onError(e);
                setTextContent(tv_content, String.valueOf(e));
            }

            @Override
            protected void onFinally()
            {
                super.onFinally();
                dismissProgress();
            }
        }.submit();
    }
}
