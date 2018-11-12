package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sd.eos.rpc.R;
import com.sd.lib.adapter.FSimpleRecyclerAdapter;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.utils.SimpleEosActionsLoader;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.pulltorefresh.FPullToRefreshView;
import com.sd.lib.pulltorefresh.PullToRefreshView;
import com.sd.lib.task.FTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class GetActionsActivity extends BaseActivity
{
    private static final String TAG = GetActionsActivity.class.getSimpleName();

    private FPullToRefreshView mPullToRefreshView;
    private RecyclerView mRecyclerView;

    private final RpcApi mRpcApi = new RpcApi("https://geo.eosasia.one");
    private String mAccountName = "ichenfq12345";
    private SimpleEosActionsLoader mActionsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_get_actions);
        mPullToRefreshView = findViewById(R.id.view_pull_to_refresh);
        mRecyclerView = findViewById(R.id.recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mPullToRefreshView.setOnRefreshCallback(new PullToRefreshView.OnRefreshCallback()
        {
            @Override
            public void onRefreshingFromHeader(PullToRefreshView view)
            {
                requestData(false);
            }

            @Override
            public void onRefreshingFromFooter(PullToRefreshView view)
            {
                if (mAdapter.getDataHolder().size() <= 0)
                    requestData(false);
                else
                {
                    if (getActionsLoader().hasNextPage())
                        requestData(true);
                    else
                    {
                        Toast.makeText(GetActionsActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        mPullToRefreshView.stopRefreshing();
                    }
                }
            }
        });
        mPullToRefreshView.startRefreshingFromHeader();
    }

    public SimpleEosActionsLoader getActionsLoader()
    {
        if (mActionsLoader == null)
        {
            mActionsLoader = new SimpleEosActionsLoader(mAccountName, -1, 0, mRpcApi)
            {
                @Override
                protected void onError(ErrorResponse errorResponse)
                {
                    Log.e(TAG, "onError:" + errorResponse.getCode() + " " + errorResponse.getMessage());
                }
            };
        }
        return mActionsLoader;
    }

    private void requestData(final boolean isLoadMore)
    {
        if (!isLoadMore)
            getActionsLoader().reset();

        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                final List<GetActionsResponse.Action> list = getActionsLoader().loadPage(100);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (isLoadMore)
                            mAdapter.getDataHolder().addData(list);
                        else
                            mAdapter.getDataHolder().setData(list);
                    }
                });
            }

            @Override
            protected void onError(Exception e)
            {
                super.onError(e);
                Log.e(TAG, e.toString());
            }

            @Override
            protected void onFinally()
            {
                super.onFinally();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mPullToRefreshView.stopRefreshing();
                    }
                });
            }
        }.submit();
    }

    private final FSimpleRecyclerAdapter<GetActionsResponse.Action> mAdapter = new FSimpleRecyclerAdapter<GetActionsResponse.Action>()
    {
        @Override
        public int getLayoutId(ViewGroup parent, int viewType)
        {
            return R.layout.item_get_actions;
        }

        @Override
        public void onBindData(FRecyclerViewHolder<GetActionsResponse.Action> holder, int position, GetActionsResponse.Action model)
        {
            TextView tv_seq = holder.get(R.id.tv_seq);
            TextView tv_account = holder.get(R.id.tv_account);
            TextView tv_name = holder.get(R.id.tv_name);
            TextView tv_time = holder.get(R.id.tv_time);
            TextView tv_data = holder.get(R.id.tv_data);
            TextView tv_trx_id = holder.get(R.id.tv_trx_id);

            tv_seq.setText(String.valueOf(model.getAccount_action_seq()));
            tv_account.setText(model.getAction_trace().getAct().getAccount());
            tv_name.setText(model.getAction_trace().getAct().getName());

            tv_data.setText(model.getAction_trace().getAct().getData());
            tv_trx_id.setText(model.getAction_trace().getTrx_id());

            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-8"));
            final String timeFormat = dateFormat.format(RpcUtils.toDate(model.getBlock_time()));
            tv_time.setText(timeFormat);

            if (model.hasInlineTraces())
                tv_seq.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            else
                tv_seq.setBackgroundColor(0);
        }
    };
}
