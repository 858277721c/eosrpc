package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sd.eos.rpc.R;
import com.sd.lib.eos.rpc.api.model.AbiJsonToBinResponse;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetBlockResponse;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.output.PushTransaction;
import com.sd.lib.eos.rpc.params.TransferActionParams;
import com.sd.lib.task.FTask;
import com.sd.lib.utils.context.FToast;

/**
 * 转账
 */
public class TransferActivity extends BaseActivity implements View.OnClickListener
{
    private EditText et_from, et_from_key_private, et_to, et_transfer_quantity, et_transfer_memo;
    private TextView tv_content;
    private FTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transfer);
        et_from = findViewById(R.id.et_from);
        et_from_key_private = findViewById(R.id.et_from_key_private);
        et_to = findViewById(R.id.et_to);
        et_transfer_quantity = findViewById(R.id.et_transfer_quantity);
        et_transfer_memo = findViewById(R.id.et_transfer_memo);
        tv_content = findViewById(R.id.tv_content);

        findViewById(R.id.btn_transfer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_transfer:
                transfer();
                break;
        }
    }

    private void transfer()
    {
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

        final String fromkeyPrivate = et_from_key_private.getText().toString();
        if (TextUtils.isEmpty(fromkeyPrivate))
        {
            FToast.show("请输入转账者账号私钥");
            return;
        }

        final String quantity = et_transfer_quantity.getText().toString();
        if (TextUtils.isEmpty(fromkeyPrivate))
        {
            FToast.show("请输入转账金额");
            return;
        }

        final String memo = et_transfer_memo.getText().toString();

        final TransferActionParams actionParams = new TransferActionParams.Builder()
                .setFrom(from)
                .setTo(to)
                .setQuantity(Double.parseDouble(quantity), null)
                .setMemo(memo)
                .build();

        cancelTask();
        mTask = new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final PushTransaction pushTransaction = new PushTransaction(actionParams);
                pushTransaction.submit(fromkeyPrivate, new PushTransaction.Callback()
                {
                    @Override
                    public void onSuccess(ApiResponse<PushTransactionResponse> response)
                    {
                        setTextContent(tv_content, new Gson().toJson(response.getSuccess()));
                    }

                    @Override
                    public void onErrorAbiJsonToBin(ApiResponse<AbiJsonToBinResponse> response, String msg)
                    {
                        setTextContent(tv_content, new Gson().toJson(response.getError()) + " " + msg);
                    }

                    @Override
                    public void onErrorGetInfo(ApiResponse<GetInfoResponse> response, String msg)
                    {
                        setTextContent(tv_content, new Gson().toJson(response.getError()) + " " + msg);
                    }

                    @Override
                    public void onErrorGetBlock(ApiResponse<GetBlockResponse> response, String msg)
                    {
                        setTextContent(tv_content, new Gson().toJson(response.getError()) + " " + msg);
                    }

                    @Override
                    public void onErrorPushTransaction(ApiResponse<PushTransactionResponse> response, String msg)
                    {
                        setTextContent(tv_content, new Gson().toJson(response.getError()) + " " + msg);
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
