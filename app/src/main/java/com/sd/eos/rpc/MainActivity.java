package com.sd.eos.rpc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sd.lib.eos.rpc.manager.PushManager;
import com.sd.lib.eos.rpc.params.BuyramActionParams;
import com.sd.lib.eos.rpc.params.DelegatebwActionParams;
import com.sd.lib.eos.rpc.params.NewaccountActionParams;
import com.sd.lib.http.task.FTask;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final NewaccountActionParams newaccountActionParams = new NewaccountActionParams.Builder()
                        .setCreator("fwtest111111")
                        .setName("lanfang12345")
                        .setOwner("EOS6tz2NBXZDQLxt5a7tiWVvfuqDgNuVDJRReKZBn5xCWroagzZa9")
                        .build();

                final BuyramActionParams buyramActionParams = new BuyramActionParams.Builder()
                        .setPayer("fwtest111111")
                        .setReceiver("lanfang12345")
                        .setQuant("1.0000 EOS")
                        .build();

                final DelegatebwActionParams delegatebwActionParams = new DelegatebwActionParams.Builder()
                        .setFrom("fwtest111111")
                        .setReceiver("lanfang12345")
                        .setStake_cpu_quantity("1.0000 EOS")
                        .setStake_net_quantity("1.0000 EOS")
                        .setTransfer(1)
                        .build();

                PushManager pushManager = new PushManager();
                pushManager.addAction(newaccountActionParams);
                pushManager.addAction(buyramActionParams);
                pushManager.addAction(delegatebwActionParams);
                pushManager.execute("5JQh92zjfWJW6a6suaLm7o2wMCNKZnqp4bb3Kb2dNSb7ts8vDWH");
            }

            @Override
            protected void onError(Exception e)
            {
                super.onError(e);
                Log.e(TAG, String.valueOf(e));
            }
        }.submit();
    }
}
