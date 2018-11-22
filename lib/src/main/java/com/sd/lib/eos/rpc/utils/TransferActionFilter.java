package com.sd.lib.eos.rpc.utils;

import android.util.Log;

import com.sd.lib.eos.rpc.api.model.GetActionsResponse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TransferActionFilter
{
    private final Map<String, GetActionsResponse.Action> mMapInline = new HashMap<>();

    public void filterPage(List<GetActionsResponse.Action> list, boolean isLoadMore)
    {
        filterInternal(list, mMapInline, true);
    }

    public void filterTotal(List<GetActionsResponse.Action> list)
    {
        filterInternal(list, null, false);
    }

    private static void filterInternal(final List<GetActionsResponse.Action> list, Map<String, GetActionsResponse.Action> mapInline, final boolean checkRepeat)
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
                if (item.hasInlineTraces())
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
            if (mapInline.containsKey(trxId) && !item.hasInlineTraces())
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
