package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sd.eos.rpc.R;
import com.sd.eos.rpc.dialog.LocalAccountDialog;
import com.sd.eos.rpc.model.AccountHolder;
import com.sd.eos.rpc.model.AccountModel;
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.PushTransactionResponse;
import com.sd.lib.eos.rpc.core.FEOSManager;
import com.sd.lib.eos.rpc.core.output.PushTransaction;
import com.sd.lib.eos.rpc.params.BuyramActionParams;
import com.sd.lib.eos.rpc.params.DelegatebwActionParams;
import com.sd.lib.eos.rpc.params.NewaccountActionParams;
import com.sd.lib.task.FTask;

import java.util.Random;

/**
 * 创建账号
 */
public class CreateAccountActivity extends BaseActivity implements View.OnClickListener
{
    private EditText et_creator, et_creator_key_private;
    private EditText et_new_account, et_buy_ram_quantity, et_stake_cpu_quantity, et_stake_net_quantity;
    private EditText et_new_account_key_private, et_new_account_key_public;
    private TextView tv_content;

    private FTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_account);
        et_creator = findViewById(R.id.et_creator);
        et_creator_key_private = findViewById(R.id.et_creator_key_private);
        et_new_account = findViewById(R.id.et_new_account);
        et_buy_ram_quantity = findViewById(R.id.et_buy_ram_quantity);
        et_stake_cpu_quantity = findViewById(R.id.et_stake_cpu_quantity);
        et_stake_net_quantity = findViewById(R.id.et_stake_net_quantity);
        et_new_account_key_private = findViewById(R.id.et_new_account_key_private);
        et_new_account_key_public = findViewById(R.id.et_new_account_key_public);
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_creator_label:
                final LocalAccountDialog dialog = new LocalAccountDialog(this);
                dialog.setCallback(new LocalAccountDialog.Callback()
                {
                    @Override
                    public void onClickItem(AccountModel model)
                    {
                        et_creator.setText(model.getAccount());
                        et_creator_key_private.setText(model.getPrivateKey());
                    }
                });
                dialog.show();
                break;
            case R.id.tv_new_account_label:
                et_new_account.setText(randomAccount());
                break;

            case R.id.tv_new_account_key_private_label:
            case R.id.tv_new_account_key_public_label:

                final com.sd.lib.eos.rpc.core.EccTool eccTool = FEOSManager.getInstance().getEccTool();

                final String privateKey = eccTool.generatePrivateKey();
                final String publicKey = eccTool.privateToPublicKey(privateKey);

                et_new_account_key_private.setText(privateKey);
                et_new_account_key_public.setText(publicKey);
                break;
            case R.id.btn_create_account:
                createAccount();
                break;
        }
    }

    private String randomAccount()
    {
        final String[] arr = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5"};

        final Random random = new Random();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++)
        {
            final int index = random.nextInt(arr.length);
            sb.append(arr[index]);
        }
        return sb.toString();
    }

    private void createAccount()
    {
        final String creator = et_creator.getText().toString();
        if (TextUtils.isEmpty(creator))
        {
            showToast("请输入创建者账号");
            return;
        }

        final String creatorKeyPrivate = et_creator_key_private.getText().toString();
        if (TextUtils.isEmpty(creatorKeyPrivate))
        {
            showToast("请输入创建者账号私钥");
            return;
        }

        final String newAccount = et_new_account.getText().toString();
        if (TextUtils.isEmpty(newAccount))
        {
            showToast("请输入新账号");
            return;
        }

        final String buyRam = et_buy_ram_quantity.getText().toString();
        if (TextUtils.isEmpty(buyRam))
        {
            showToast("请输入要购买的内存金额");
            return;
        }

        final String stakeCpu = et_stake_cpu_quantity.getText().toString();
        if (TextUtils.isEmpty(stakeCpu))
        {
            showToast("请输入要抵押的cpu金额");
            return;
        }

        final String stakeNet = et_stake_net_quantity.getText().toString();
        if (TextUtils.isEmpty(stakeNet))
        {
            showToast("请输入要抵押的net金额");
            return;
        }

        final String newAccountKeyPublic = et_new_account_key_public.getText().toString();
        final String newAccountKeyPrivate = et_new_account_key_private.getText().toString();
        if (TextUtils.isEmpty(newAccountKeyPublic) || TextUtils.isEmpty(newAccountKeyPrivate))
        {
            showToast("请生成密钥");
            return;
        }

        cancelTask();
        mTask = new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final NewaccountActionParams newaccountActionParams = new NewaccountActionParams.Builder()
                        .setCreator(creator)
                        .setNewAccount(newAccount)
                        .setOwner(newAccountKeyPublic)
                        .build();

                final BuyramActionParams buyramActionParams = new BuyramActionParams.Builder()
                        .setPayer(creator)
                        .setReceiver(newAccount)
                        .setQuantity(Double.valueOf(buyRam), null)
                        .build();

                final DelegatebwActionParams delegatebwActionParams = new DelegatebwActionParams.Builder()
                        .setFrom(creator)
                        .setReceiver(newAccount)
                        .setStake_cpu_quantity(Double.parseDouble(stakeCpu), null)
                        .setStake_net_quantity(Double.parseDouble(stakeNet), null)
                        .setTransfer(1)
                        .build();

                final PushTransaction pushTransaction = new PushTransaction(newaccountActionParams, buyramActionParams, delegatebwActionParams);
                pushTransaction.submit(creatorKeyPrivate, new PushTransaction.Callback()
                {
                    @Override
                    public void onSuccess(ApiResponse<PushTransactionResponse> response)
                    {
                        setTextContent(tv_content, new Gson().toJson(response.getSuccess()));
                        AccountHolder.get().add(new AccountModel(newAccount, newAccountKeyPrivate, newAccountKeyPublic));
                    }

                    @Override
                    public void onErrorApi(PushTransaction.ApiError error, ErrorResponse errorResponse)
                    {
                        super.onErrorApi(error, errorResponse);
                        setTextContent(tv_content, errorResponse.getErrorInformation());
                    }

                    @Override
                    public void onError(PushTransaction.Error error, String msg)
                    {
                        super.onError(error, msg);
                        setTextContent(tv_content, msg);
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
