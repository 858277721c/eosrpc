package com.sd.eos.rpc.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.util.List;

public abstract class PermissionChecker
{
    private final Context mContext;
    private final String[] mPermissions;

    public PermissionChecker(Context context)
    {
        mContext = context;
        mPermissions = getPermissions();

        if (mPermissions == null || mPermissions.length <= 0)
            throw new RuntimeException();
    }

    public final Context getContext()
    {
        return mContext;
    }

    /**
     * 返回要检测的权限{@link Permission}
     *
     * @return
     */
    protected abstract String[] getPermissions();

    /**
     * 开始检测权限
     */
    public final void check()
    {
        AndPermission.with(getContext())
                .runtime()
                .permission(mPermissions)
                .onGranted(new Action<List<String>>()
                {
                    @Override
                    public void onAction(List<String> strings)
                    {
                        PermissionChecker.this.onGrantedInternal(strings);
                    }
                })
                .onDenied(new Action<List<String>>()
                {
                    @Override
                    public void onAction(List<String> strings)
                    {
                        final boolean hasAlwaysDeniedPermission = AndPermission.hasAlwaysDeniedPermission(getContext(), strings);
                        PermissionChecker.this.onDeniedInternal(strings, hasAlwaysDeniedPermission);
                    }
                }).start();
    }

    private void onGrantedInternal(List<String> permissions)
    {
        onGranted(permissions);
    }

    private void onDeniedInternal(final List<String> permissions, final boolean hasAlwaysDeniedPermission)
    {
        if (hasAlwaysDeniedPermission)
        {
            final List<String> permissionNames = Permission.transformText(getContext(), permissions);
            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setTitle("提示")
                    .setMessage("App需要以下权限才能正常运行：\r\n" + TextUtils.join("\r\n", permissionNames))
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            AndPermission.with(getContext())
                                    .runtime()
                                    .setting()
                                    .onComeback(new Setting.Action()
                                    {
                                        @Override
                                        public void onAction()
                                        {
                                            final String[] arr = new String[permissions.size()];
                                            permissions.toArray(arr);
                                            if (AndPermission.hasPermissions(getContext(), arr))
                                            {
                                                onGranted(permissions);
                                            } else
                                            {
                                                onDenied(permissions, hasAlwaysDeniedPermission);
                                            }
                                        }
                                    }).start();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            onDenied(permissions, hasAlwaysDeniedPermission);
                        }
                    })
                    .show();
        } else
        {
            onDenied(permissions, hasAlwaysDeniedPermission);
        }
    }

    /**
     * 权限被允许
     *
     * @param permissions
     */
    protected abstract void onGranted(List<String> permissions);

    /**
     * 权限被拒绝，默认实现，退出App
     *
     * @param permissions
     * @param hasAlwaysDeniedPermission
     */
    protected void onDenied(final List<String> permissions, boolean hasAlwaysDeniedPermission)
    {

    }
}
