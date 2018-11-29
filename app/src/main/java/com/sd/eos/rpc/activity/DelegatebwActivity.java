package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sd.eos.rpc.R;
import com.sd.eos.rpc.dialog.LocalAccountDialog;
import com.sd.eos.rpc.model.AccountModel;
import com.sd.lib.eos.rpc.api.ApiType;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.output.PushTransaction;
import com.sd.lib.eos.rpc.params.DelegatebwActionParams;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.task.FTask;

/**
 * 购买抵押
 */
public class DelegatebwActivity extends BaseActivity implements View.OnClickListener
{
    private EditText et_from, et_from_key_private, et_to, et_stake_cpu_quantity, et_stake_net_quantity;
    private TextView tv_content;
    private FTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delegatebw);
        et_from = findViewById(R.id.et_from);
        et_from_key_private = findViewById(R.id.et_from_key_private);
        et_stake_cpu_quantity = findViewById(R.id.et_stake_cpu_quantity);
        et_stake_net_quantity = findViewById(R.id.et_stake_net_quantity);
        et_to = findViewById(R.id.et_to);
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_from_label:
                final LocalAccountDialog dialogFrom = new LocalAccountDialog(this);
                dialogFrom.setCallback(new LocalAccountDialog.Callback()
                {
                    @Override
                    public void onClickItem(AccountModel model)
                    {
                        et_from.setText(model.getAccount());
                        et_from_key_private.setText(model.getPrivateKey());
                    }
                });
                dialogFrom.show();
                break;
            case R.id.tv_to_label:
                final LocalAccountDialog dialogTo = new LocalAccountDialog(this);
                dialogTo.setCallback(new LocalAccountDialog.Callback()
                {
                    @Override
                    public void onClickItem(AccountModel model)
                    {
                        et_to.setText(model.getAccount());
                    }
                });
                dialogTo.show();
                break;
            case R.id.btn_buy:
                buyRam();
                break;
        }
    }

    private void buyRam()
    {
        final String from = et_from.getText().toString();
        if (TextUtils.isEmpty(from))
        {
            showToast("请输入购买账号");
            return;
        }

        final String to = et_to.getText().toString();
        if (TextUtils.isEmpty(from))
        {
            showToast("请输入接收账号");
            return;
        }

        final String fromkeyPrivate = et_from_key_private.getText().toString();
        if (TextUtils.isEmpty(fromkeyPrivate))
        {
            showToast("请输入购买账号私钥");
            return;
        }

        final String cpuQuantity = et_stake_cpu_quantity.getText().toString();
        if (TextUtils.isEmpty(cpuQuantity))
        {
            showToast("请输入cpu抵押金额");
            return;
        }

        final String netQuantity = et_stake_net_quantity.getText().toString();
        if (TextUtils.isEmpty(netQuantity))
        {
            showToast("请输入net抵押金额");
            return;
        }

        final DelegatebwActionParams params = new DelegatebwActionParams.Builder()
                .setFrom(from)
                .setReceiver(to)
                .setStake_cpu_quantity(Double.parseDouble(cpuQuantity), null)
                .setStake_net_quantity(Double.parseDouble(netQuantity), null)
                .build();

        cancelTask();
        mTask = new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                new PushTransaction(params)
                {
                    @Override
                    protected void onSuccess(ApiResponse<PushTransactionResponse> response)
                    {
                        setTextContent(tv_content, new Gson().toJson(response.getSuccess()));
                    }

                    @Override
                    protected void onErrorApi(ApiType apiType, ErrorResponse errorResponse)
                    {
                        setTextContent(tv_content, RpcUtils.getErrorCodeMessage(errorResponse, DelegatebwActivity.this));
                    }

                    @Override
                    protected void onError(Error error, String msg)
                    {
                        setTextContent(tv_content, msg);
                    }
                }.submit(fromkeyPrivate);
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
        };

        showProgress("");
        mTask.submit();
    }

    private void cancelTask()
    {
        if (mTask != null)
            mTask.cancel(true);
        setTextContent(tv_content, "");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        cancelTask();
    }
}
