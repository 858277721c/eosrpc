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
import com.sd.lib.eos.rpc.api.model.ApiResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.pulltorefresh.FPullToRefreshView;
import com.sd.lib.pulltorefresh.PullToRefreshView;
import com.sd.lib.task.FTask;

import java.util.Collections;
import java.util.List;

public class GetActionsActivity extends BaseActivity
{
    public static final String TAG = GetActionsActivity.class.getSimpleName();

    private static final int PAGE_SIZE = 100;

    private FPullToRefreshView mPullToRefreshView;
    private RecyclerView mRecyclerView;

    private final RpcApi mRpcApi = new RpcApi();

    private String mAccountName = "liuliqin1234";
    private int mPosition;
    private int mOffset = -PAGE_SIZE;

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
                mPosition = -1;
                requestData(false);
            }

            @Override
            public void onRefreshingFromFooter(PullToRefreshView view)
            {
                if (mPosition > 0)
                {
                    requestData(true);
                } else
                {
                    Toast.makeText(GetActionsActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    mPullToRefreshView.stopRefreshing();
                }
            }
        });
        mPullToRefreshView.startRefreshingFromHeader();
    }

    private void requestData(final boolean isLoadMore)
    {
        new FTask()
        {
            @Override
            protected void onRun() throws Exception
            {
                Log.i(TAG, "requestData:" + mAccountName + " " + mPosition + " " + mOffset);
                final ApiResponse<GetActionsResponse> apiResponse = mRpcApi.getActions(mAccountName, mPosition, mOffset);
                if (apiResponse.isSuccessful())
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            final List<GetActionsResponse.Action> list = apiResponse.getSuccess().getActions();
                            if (list != null && !list.isEmpty())
                            {
                                Log.i(TAG, "list size:" + list.size());
                                mPosition = list.get(0).getAccount_action_seq() - 1;
                                Log.i(TAG, "next position:" + mPosition);
                                Collections.reverse(list);
                            }

                            if (isLoadMore)
                                mAdapter.getDataHolder().addData(list);
                            else
                                mAdapter.getDataHolder().setData(list);
                        }
                    });
                }
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

            tv_seq.setText(String.valueOf(model.getAccount_action_seq()));
            tv_account.setText(model.getAction_trace().getAct().getAccount());
            tv_name.setText(model.getAction_trace().getAct().getName());
        }
    };
}
