package com.sd.eos.rpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sd.eos.rpc.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_get_info:
                startActivity(new Intent(this, GetInfoActivity.class));
                break;
            case R.id.btn_get_account:
                startActivity(new Intent(this, GetAccountActivity.class));
                break;
            case R.id.btn_get_currency:
                startActivity(new Intent(this, GetCurrencyActivity.class));
                break;
            case R.id.btn_create_account:
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
        }
    }
}
