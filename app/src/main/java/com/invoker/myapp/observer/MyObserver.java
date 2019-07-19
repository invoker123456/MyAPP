package com.invoker.myapp.observer;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by invoker on 2019-03-18
 * Description: 重写Observer
 */
public class MyObserver<T> implements Observer<T> {
    private static final String TAG = "MyObserver";
    private ObserverOnNextListener listener;
    private Context context;

    public MyObserver(Context context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.i(TAG, "onSubscribe: ");
        //添加业务处理
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError: " + e);
        //添加业务处理
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete: ");
        //添加业务处理
    }
}
