package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.model.GetActionsResponse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class TransferActionFilter
{
    private final boolean mIsReverse;

    private final Map<String, GetActionsResponse.Action> mMapInline = new HashMap<>();
    private List<GetActionsResponse.Action> mListLastPage;

    public TransferActionFilter(boolean isReverse)
    {
        mIsReverse = isReverse;
    }

    public final void filterPage(List<GetActionsResponse.Action> list, boolean isLoadMore)
    {
        final Boolean isReverseList = isReverseList(list);
        if (isReverseList == null)
            return;

        if (isReverseList != mIsReverse)
            throw new IllegalArgumentException("list must be reverse = " + mIsReverse);

        if (mIsReverse)
        {
            filter(list, mMapInline, true);

            if (list != null && !list.isEmpty() && mListLastPage != null && !mListLastPage.isEmpty())
            {
                final GetActionsResponse.Action actionFirst = list.get(0);
                final int inlineSize = actionFirst.getInlineTracesSize();
                if (inlineSize > 0)
                {
                    final String trxId = actionFirst.getAction_trace().getTrx_id();

                    final List<GetActionsResponse.Action> listRemove = new LinkedList<>();
                    for (int i = mListLastPage.size() - 1; i >= 0; i--)
                    {
                        final GetActionsResponse.Action item = mListLastPage.get(i);
                        if (trxId.equals(item.getAction_trace().getTrx_id()))
                        {
                            listRemove.add(item);
                            Log.e(TransferActionFilter.class.getSimpleName(), "should remove last page action:" + item.getAccount_action_seq());
                        } else
                        {
                            break;
                        }
                    }

                    if (!listRemove.isEmpty())
                        removeLastPageActions(listRemove);
                }
            }

            mListLastPage = list;
        } else
        {
            filter(list, mMapInline, false);
        }
    }

    protected abstract void removeLastPageActions(List<GetActionsResponse.Action> list);

    private Boolean isReverseList(List<GetActionsResponse.Action> list)
    {
        if (list == null || list.isEmpty())
            return null;

        final int startSeq = list.get(0).getAccount_action_seq();
        final int endSeq = list.get(list.size() - 1).getAccount_action_seq();

        return startSeq > endSeq;
    }

    public static void filter(final List<GetActionsResponse.Action> list, Map<String, GetActionsResponse.Action> mapInline, final boolean checkRepeat)
    {
        if (list == null || list.isEmpty())
            return;

        if (mapInline == null)
            mapInline = new HashMap<>();

        final Map<String, List<GetActionsResponse.Action>> mapRepeatActions = checkRepeat ? new HashMap<String, List<GetActionsResponse.Action>>() : null;

        Iterator<GetActionsResponse.Action> it = list.iterator();
        while (it.hasNext())
        {
            final GetActionsResponse.Action item = it.next();

            final String name = item.getAction_trace().getAct().getName();
            if ("transfer".equals(name))
            {
                final String trxId = item.getAction_trace().getTrx_id();
                if (item.getInlineTracesSize() > 0)
                    mapInline.put(trxId, item);

                if (checkRepeat)
                {
                    List<GetActionsResponse.Action> listMap = mapRepeatActions.get(trxId);
                    if (listMap == null)
                    {
                        listMap = new LinkedList<>();
                        mapRepeatActions.put(trxId, listMap);
                    }
                    listMap.add(item);
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

            final String trxId = item.getAction_trace().getTrx_id();
            if (mapInline.containsKey(trxId) && item.getInlineTracesSize() <= 0)
            {
                it.remove();

                if (checkRepeat)
                    mapRepeatActions.remove(trxId);
                Log.i(TransferActionFilter.class.getSimpleName(), "remove inline action:" + item.getAccount_action_seq());
            }
        }

        if (checkRepeat)
        {
            for (List<GetActionsResponse.Action> itemList : mapRepeatActions.values())
            {
                if (itemList.size() > 1)
                {
                    boolean allEquals = true;
                    final GetActionsResponse.Action itemFirst = itemList.get(0);
                    for (int i = 1; i < itemList.size(); i++)
                    {
                        final GetActionsResponse.Action item = itemList.get(i);
                        if (!itemFirst.getAction_trace().getAct().equals(item.getAction_trace().getAct()))
                        {
                            allEquals = false;
                            break;
                        }
                    }

                    if (allEquals)
                    {
                        for (GetActionsResponse.Action item : itemList)
                        {
                            list.remove(item);
                            Log.i(TransferActionFilter.class.getSimpleName(), "remove repeat action:" + item.getAccount_action_seq());
                        }
                    }
                }
            }
        }
    }
}
