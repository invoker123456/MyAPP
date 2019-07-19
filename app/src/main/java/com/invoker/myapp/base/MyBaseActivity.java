package com.invoker.myapp.base;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.invoker.myapp.main.LoadingActivity;
import com.invoker.myapp.slideBack.ActionBarActivity;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * Created by invoker on 2018-03-17.
 */

public class MyBaseActivity extends ActionBarActivity {
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = true;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    public ZLoadingDialog dialog;

    private static Dialog loadingDialog;

    /**
     * 这里要隐藏掉整个ActionBar
     * 因为ActionBarActivity 会显示
     * 否者重复显示
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏掉整个ActionBar，包括下面的Tabs
        getSupportActionBar().hide();

        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.PAC_MAN)//设置类型
                .setLoadingColor(Color.GREEN)//颜色
                .setHintText("Loading...")
                .setHintTextSize(14)
                //.setCanceledOnTouchOutside(false)
                //.setCancelable(false)
                .create();
    }

    /**
     * [日志输出]
     *
     * @param msg
     */
    protected void $Log(String msg) {
        Log.i(TAG, msg);
    }

    /**
     * [简化Toast]
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void NewActivity(Class<?> clz) {
        startActivity(new Intent(MyBaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void NewActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
