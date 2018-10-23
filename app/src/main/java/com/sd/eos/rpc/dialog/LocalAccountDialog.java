package com.sd.eos.rpc.dialog;

import android.app.Activity;
import android.view.View;

import com.sd.eos.rpc.model.AccountHolder;
import com.sd.eos.rpc.model.AccountModel;
import com.sd.lib.dialoger.Dialoger;
import com.sd.lib.dialoger.impl.FDialoger;
import com.sd.lib.dialogview.DialogMenuView;
import com.sd.lib.dialogview.impl.FDialogMenuView;

import java.util.List;

public class LocalAccountDialog extends FDialoger
{
    private FDialogMenuView mMenuView;
    private final List<AccountModel> mListModel = AccountHolder.get().getAllAccount();

    private Callback mCallback;

    public LocalAccountDialog(Activity activity)
    {
        super(activity);
        setContentView(getMenuView());
        getMenuView().setItems(mListModel.toArray());
    }

    public void setCallback(Callback callback)
    {
        mCallback = callback;
    }

    private FDialogMenuView getMenuView()
    {
        if (mMenuView == null)
        {
            mMenuView = new FDialogMenuView(getOwnerActivity())
            {
                @Override
                public Dialoger getDialoger()
                {
                    return LocalAccountDialog.this;
                }
            };
            mMenuView.setCallback(new DialogMenuView.Callback()
            {
                @Override
                public void onClickItem(View v, int index, DialogMenuView view)
                {
                    super.onClickItem(v, index, view);
                    final AccountModel model = mListModel.get(index);
                    mCallback.onClickItem(model);
                }
            });
        }
        return mMenuView;
    }

    public interface Callback
    {
        void onClickItem(AccountModel model);
    }
}
