package com.invoker.myapp.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by invoker on 2019-03-18
 * Description: 编写ProgressDialog类代码
 * Handler接收两个消息来控制显示Dialog还是关闭Dialog。
 */
public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    //原生的 可以监听 取消事件
    private ProgressDialog pd;
//    private ZLoadingDialog dialog;   // 加载 第三方 Progress

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.cancelable = cancelable;
        this.mProgressCancelListener = mProgressCancelListener;

//        dialog = new ZLoadingDialog(context);
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setMessage("疯狂加载中...");
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!pd.isShowing()) {
                pd.show();
            }
        }

//        dialog.setLoadingBuilder(Z_TYPE.PAC_MAN)//设置类型
//                .setLoadingColor(Color.GREEN)//颜色
//                .setHintText("疯狂加载中...")
//                .setHintTextSize(14)
//                .setCanceledOnTouchOutside(cancelable)
//                .setCancelable(cancelable)
//                .show();

    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }

//        if (dialog != null) {
//            dialog.dismiss();
//            dialog = null;
//        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
