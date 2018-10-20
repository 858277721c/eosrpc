package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sd.eos.rpc.R;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.GetInfoResponse;
import com.sd.lib.task.FTask;

/**
 * 查询区块链信息
 */
public class GetInfoActivity extends AppCompatActivity
{
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_get_info);
        tv_content = findViewById(R.id.tv_content);

        findViewById(R.id.btn_get_info).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getInfo();
            }
        });
    }

    private void getInfo()
    {
        tv_content.setText("");
        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final GetInfoResponse response = new RpcApi().getInfo();

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        tv_content.setText(new Gson().toJson(response));
                    }
                });
            }
        }.submit();
    }
}
