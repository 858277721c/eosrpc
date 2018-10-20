package com.sd.eos.rpc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.GetAccountResponse;
import com.sd.lib.eos.rpc.manager.PushManager;
import com.sd.lib.eos.rpc.params.BuyramActionParams;
import com.sd.lib.eos.rpc.params.DelegatebwActionParams;
import com.sd.lib.eos.rpc.params.NewaccountActionParams;
import com.sd.lib.http.task.FTask;

public class MainActivity extends AppCompatActivity
{
    public static final String CREATER_ACCOUNT = "fwtest111111";
    public static final String CREATER_KEY_PRIVATE = "5JQh92zjfWJW6a6suaLm7o2wMCNKZnqp4bb3Kb2dNSb7ts8vDWH";
    public static final String CREATER_KEY_PUBLIC = "EOS67Q8sXouXtxi72FFgih5hWx7Ks1Bkm8soNuzt9U9sRNuMtjHwT";


    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createAccount("lanfang12345", "EOS6tz2NBXZDQLxt5a7tiWVvfuqDgNuVDJRReKZBn5xCWroagzZa9");
        getAccount("lanfang12345");
    }

    private void createAccount(final String name, final String publicKey)
    {
        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final NewaccountActionParams newaccountActionParams = new NewaccountActionParams.Builder()
                        .setCreator(CREATER_ACCOUNT)
                        .setName(name)
                        .setOwner(publicKey)
                        .addAuthorization(CREATER_ACCOUNT)
                        .build();

                final BuyramActionParams buyramActionParams = new BuyramActionParams.Builder()
                        .setPayer(CREATER_ACCOUNT)
                        .setReceiver(name)
                        .setQuant("1.0000 EOS")
                        .addAuthorization(CREATER_ACCOUNT)
                        .build();

                final DelegatebwActionParams delegatebwActionParams = new DelegatebwActionParams.Builder()
                        .setFrom(CREATER_ACCOUNT)
                        .setReceiver(name)
                        .setStake_cpu_quantity("1.0000 EOS")
                        .setStake_net_quantity("1.0000 EOS")
                        .setTransfer(1)
                        .addAuthorization(CREATER_ACCOUNT)
                        .build();

                PushManager pushManager = new PushManager();
                pushManager.addAction(newaccountActionParams);
                pushManager.addAction(buyramActionParams);
                pushManager.addAction(delegatebwActionParams);
                pushManager.execute(CREATER_KEY_PRIVATE);
            }

            @Override
            protected void onError(Exception e)
            {
                super.onError(e);
                Log.e(TAG, String.valueOf(e));
            }
        }.submit();
    }

    private void getAccount(final String accountName)
    {
        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final GetAccountResponse response = new RpcApi().getAccount(accountName);
                Log.i(TAG, "getAccount:" + response.getAccount_name());
            }
        }.submit();
    }
}
