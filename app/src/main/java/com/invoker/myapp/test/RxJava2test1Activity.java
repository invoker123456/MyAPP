package com.invoker.myapp.test;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by invoker on 2019-03-11
 * Description:
 */
public class RxJava2test1Activity extends MyBaseActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2_news);
        context = this;
        ButterKnife.bind(this);

        //  基本
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Log.i(TAG, "emitter: 1");
                        emitter.onNext(1);
                        Log.i(TAG, "emitter: 2");
                        emitter.onNext(2);
                        Log.i(TAG, "emitter: 3");
                        emitter.onNext(3);
                        Log.i(TAG, "emitter: complete");
                        emitter.onComplete();
                        Log.i(TAG, "emitter: 4");
                        emitter.onNext(4);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    private Disposable mDisposable;
                    private int i;

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "onNext: " + integer);
                        i++;
                        if (i == 2) {
                            Log.i(TAG, "dispose");
                            mDisposable.dispose();
                            Log.i(TAG, "isDisposed : " + mDisposable.isDisposed());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //  map
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "this is result " + integer;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept: " + s);
                    }
                });
    }
}
