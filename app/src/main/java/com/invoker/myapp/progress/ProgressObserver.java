package com.invoker.myapp.progress;

import android.content.Context;
import android.util.Log;

import com.invoker.myapp.observer.ObserverOnNextListener;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by invoker on 2019-03-18
 * Description: 重写 改进Observer -> ProgressObserver
 */
public class ProgressObserver<T> implements Observer<T>, ProgressCancelListener {
    private static final String TAG = "ProgressObserver";
    private ObserverOnNextListener listener;
    private ProgressDialogHandler mProgressDialogHandler;   // 原来用的 系统自带的 Progress
    private Context context;
    private Disposable d;

    public ProgressObserver(Context context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
        // cancelable: true : 可以取消
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
//        Log.i(TAG, "onError: " + e.getMessage());
        e.printStackTrace();
        d.dispose();
        dismissProgressDialog();
        //添加业务处理
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete: ");
        d.dispose();
        dismissProgressDialog();
        //添加业务处理
    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
        }
    }
}
