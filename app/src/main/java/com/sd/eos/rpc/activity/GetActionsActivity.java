package com.sd.eos.rpc.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sd.eos.rpc.R;
import com.sd.lib.adapter.FSimpleRecyclerAdapter;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.eos.rpc.api.RpcApi;
import com.sd.lib.eos.rpc.api.model.ErrorResponse;
import com.sd.lib.eos.rpc.api.model.GetActionsResponse;
import com.sd.lib.eos.rpc.utils.EosActionsBoundLoader;
import com.sd.lib.eos.rpc.utils.RpcUtils;
import com.sd.lib.pulltorefresh.FPullToRefreshView;
import com.sd.lib.pulltorefresh.PullToRefreshView;
import com.sd.lib.task.FTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class GetActionsActivity extends BaseActivity
{
    private static final String TAG = GetActionsActivity.class.getSimpleName();

    private FPullToRefreshView mPullToRefreshView;
    private RecyclerView mRecyclerView;

    private final RpcApi mRpcApi = new RpcApi("https://geo.eosasia.one");

    private String mAccountName = "ichenfq12345";
    private final Map<String, String> mMapInline = new HashMap<>();

    private EosActionsBoundLoader mActionsLoader;

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
                requestData(true);
            }
        });
        mPullToRefreshView.startRefreshingFromHeader();
    }

    public EosActionsBoundLoader getActionsLoader()
    {
        if (mActionsLoader == null)
        {
            mActionsLoader = new EosActionsBoundLoader(mAccountName, 1, 1, mRpcApi)
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
                final List<GetActionsResponse.Action> list = getActionsLoader().loadNextPage();
                if (list != null && !list.isEmpty())
                {
                    Log.i(TAG, "list size:" + list.size());

                    filterAction(list);
                    Log.i(TAG, "list size filter:" + list.size());

                    if (!list.isEmpty())
                    {
                        final GetActionsResponse.Action action = list.get(list.size() - 1);
                        final List<GetActionsResponse.Action> listChecked = checkInlineAction(action);
                        if (!listChecked.isEmpty())
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    for (GetActionsResponse.Action item : listChecked)
                                    {
                                        mAdapter.getDataHolder().removeData(item);
                                    }
                                }
                            });
                        }
                    }
                }

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

    private void filterAction(List<GetActionsResponse.Action> list)
    {
        if (list == null || list.isEmpty())
            return;

        Iterator<GetActionsResponse.Action> it = list.iterator();
        while (it.hasNext())
        {
            final GetActionsResponse.Action item = it.next();

            final String name = item.getAction_trace().getAct().getName();
            if ("transfer".equals(name))
            {
                if (item.hasInlineTraces())
                {
                    mMapInline.put(item.getAction_trace().getTrx_id(), "");
                }
            } else
            {
                it.remove();
            }
        }

        it = list.iterator();
        while (it.hasNext())
        {
            final GetActionsResponse.Action item = it.next();

            if (mMapInline.containsKey(item.getAction_trace().getTrx_id()) && !item.hasInlineTraces())
            {
                it.remove();
            }
        }
    }

    private List<GetActionsResponse.Action> checkInlineAction(GetActionsResponse.Action action)
    {
        final List<GetActionsResponse.Action> result = new ArrayList<>();
        if (action.hasInlineTraces())
        {
            final List<GetActionsResponse.Action> list = mAdapter.getDataHolder().getData();
            if (list != null && !list.isEmpty())
            {
                final int listSize = list.size();
                final int inlineSize = action.getAction_trace().getInline_traces().size();
                final int start = listSize - inlineSize;
                for (int i = start; i < listSize; i++)
                {
                    final GetActionsResponse.Action item = list.get(i);
                    if (mMapInline.containsKey(item.getAction_trace().getTrx_id()) && !item.hasInlineTraces())
                    {
                        result.add(item);
                    }
                }
            }
        }
        return result;
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

            final GetActionsResponse.Action.ActionTrace.Act.TransferData data = model.getAction_trace().getAct().getTransferData();
            if (data != null)
            {
                final StringBuilder sb = new StringBuilder();
                sb.append(data.getFrom()).append(" -> ").append(data.getTo());
                sb.append("\n");

                if (mAccountName.equals(data.getFrom()))
                    sb.append("-");
                else if (mAccountName.equals(data.getTo()))
                    sb.append("+");

                sb.append(" ").append(data.getQuantity());
                tv_data.setText(sb);
            }

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
