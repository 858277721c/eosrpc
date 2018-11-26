package com.sd.eos.rpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sd.eos.rpc.R;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.AbiBinToJsonResponse;
import com.sd.lib.task.FTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final AbiBinToJsonResponse response = new RpcApi("https://node.eosflare.io").abiBinToJson(
                        "zkstokensr4u",
                        "claim",
                        "40860853bf3ff5fb005a4b5300000000").getSuccess();

                response.getArgs();
            }
        }.submit();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_get_account:
                startActivity(new Intent(this, GetAccountActivity.class));
                break;
            case R.id.btn_create_account:
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
            case R.id.btn_transfer:
                startActivity(new Intent(this, TransferActivity.class));
                break;
            case R.id.btn_buy_ram:
                startActivity(new Intent(this, BuyRamActivity.class));
                break;
            case R.id.btn_delegatebw:
                startActivity(new Intent(this, DelegatebwActivity.class));
                break;
            case R.id.btn_get_actions:
                startActivity(new Intent(this, GetActionsActivity.class));
                break;
            case R.id.btn_transfer_actions:
                startActivity(new Intent(this, TransferActionsActivity.class));
                break;
        }
    }
}
