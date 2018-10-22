package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sd.eos.rpc.R;
import com.sd.eos.rpc.eos4j.ecc.EccTool;
import com.sd.lib.eos.rpc.handler.CreateAccountHandler;
import com.sd.lib.task.FTask;
import com.sd.lib.utils.context.FToast;

import java.util.UUID;

/**
 * 创建账号
 */
public class CreateAccountActivity extends BaseActivity implements View.OnClickListener
{
    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    private EditText et_creater, et_creater_key_private;
    private EditText et_new_account, et_buy_ram_quantity, et_stake_cpu_quantity, et_stake_net_quantity;
    private TextView tv_new_account_key_private, tv_new_account_key_public;

    private FTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_account);
        et_creater = findViewById(R.id.et_creater);
        et_creater_key_private = findViewById(R.id.et_creater_key_private);
        et_new_account = findViewById(R.id.et_new_account);
        et_buy_ram_quantity = findViewById(R.id.et_buy_ram_quantity);
        et_stake_cpu_quantity = findViewById(R.id.et_stake_cpu_quantity);
        et_stake_net_quantity = findViewById(R.id.et_stake_net_quantity);
        tv_new_account_key_private = findViewById(R.id.tv_new_account_key_private);
        tv_new_account_key_public = findViewById(R.id.tv_new_account_key_public);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_new_keys:
                final String privateKey = EccTool.seedPrivate(UUID.randomUUID().toString());
                final String publicKey = EccTool.privateToPublic(privateKey);

                tv_new_account_key_private.setText(privateKey);
                tv_new_account_key_public.setText(publicKey);
                break;
            case R.id.btn_create_account:
                createAccount();
                break;
        }
    }

    private void createAccount()
    {
        final String creater = et_creater.getText().toString();
        if (TextUtils.isEmpty(creater))
        {
            FToast.show("请输入创建者账号");
            return;
        }

        final String createrKeyPrivate = et_creater_key_private.getText().toString();
        if (TextUtils.isEmpty(createrKeyPrivate))
        {
            FToast.show("请输入创建者账号私钥");
            return;
        }

        final String newAccount = et_new_account.getText().toString();
        if (TextUtils.isEmpty(newAccount))
        {
            FToast.show("请输入新账号");
            return;
        }

        final String buyRam = et_buy_ram_quantity.getText().toString();
        if (TextUtils.isEmpty(buyRam))
        {
            FToast.show("请输入要购买的内存金额");
            return;
        }

        final String stakeCpu = et_stake_cpu_quantity.getText().toString();
        if (TextUtils.isEmpty(stakeCpu))
        {
            FToast.show("请输入要抵押的cpu金额");
            return;
        }

        final String stakeNet = et_stake_net_quantity.getText().toString();
        if (TextUtils.isEmpty(stakeNet))
        {
            FToast.show("请输入要抵押的net金额");
            return;
        }

        final String newAccountKeyPublic = tv_new_account_key_public.getText().toString();
        final String newAccountKeyPrivate = tv_new_account_key_private.getText().toString();
        if (TextUtils.isEmpty(newAccountKeyPublic) || TextUtils.isEmpty(newAccountKeyPrivate))
        {
            FToast.show("请生成密钥");
            return;
        }

        cancelTask();
        mTask = new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                showProgress("");

                final CreateAccountHandler handler = new CreateAccountHandler.Builder()
                        .setCreator(creater)
                        .setName(newAccount)
                        .setOwner(newAccountKeyPublic)
                        .setBuyRamQuantity(buyRam + " EOS")
                        .setStake_cpu_quantity(stakeCpu + " EOS")
                        .setStake_net_quantity(stakeNet + " EOS")
                        .setTransfer(1)
                        .build();

                handler.create(createrKeyPrivate);
            }

            @Override
            protected void onError(Exception e)
            {
                super.onError(e);
                FToast.show(String.valueOf(e));
            }

            @Override
            protected void onFinally()
            {
                super.onFinally();
                dismissProgress();
            }
        };
        mTask.submit();
    }

    private void cancelTask()
    {
        if (mTask != null)
            mTask.cancel(true);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        cancelTask();
    }
}
