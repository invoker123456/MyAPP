package com.invoker.myapp.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by invoker on 2019-03-05
 * Description:
 */
public class RxJava2Activity extends MyBaseActivity {
    @BindView(R.id.tv_download)
    TextView tv_download;
    @BindView(R.id.tv_download_result)
    TextView mTvDownloadResult;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_download, R.id.tv_flowable})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
                test();
//                test_concatMap();
                break;
            case R.id.tv_flowable:
                test_Flowable();
                break;
        }
    }

    private void test() {
        final Observable observable = Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        for (int i = 0; i < 100; i++) {
                            if (i % 2 == 0) {
                                try {
                                    Thread.sleep(500); //模拟下载的操作。
                                } catch (Exception e) {
                                    if (!emitter.isDisposed()) {
                                        emitter.onError(e);
                                    }
                                }
                                emitter.onNext(i);
                            }
                        }
                        emitter.onComplete();
                    }
                });

        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer value) {
                Log.i("BackgroundActivity", "onNext=" + value);
                mTvDownloadResult.setText("Current Progress=" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("BackgroundActivity", "onError=" + e);
                mTvDownloadResult.setText("Download Error");
            }

            @Override
            public void onComplete() {
                Log.i("BackgroundActivity", "onComplete");
                mTvDownloadResult.setText("Download onComplete");
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    // concatMap /  flatMap 的使用
    private void test_concatMap() {
        Observable.fromArray(1, 2, 3, 4, 5)
                .concatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        int delay = 0;
                        if (integer == 3) {
                            delay = 500;
                        }
                        return Observable.just(integer * 10).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "accept: " + integer);
                    }
                });
    }

    /**
     * 响应式拉取了，在某一些场景下，可以在发送事件前先判断当前的requested的值是否大于0，若等于0则说明下游处理不过来了，则需要等待
     * Flowable   上游变成了Flowable, 下游变成了Subscriber
     * 用来解决  上游疯狂发 下游却接收的很慢的问题
     * 上游一次发送最多 128 个事件, 然后大等待下游处理
     * 下游从0开始处理,处理到95 ,就会通知上游 ,上有又开始发送128个
     * (下游 叶问打倒96个, 上游等待结束,开始有发送128个给下游处理)
     */
    // Flowable 的使用
    private void test_Flowable() {

        // 上游在io线程 , 下游在主线程
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.i(TAG, "First requested = " + emitter.requested());
                        boolean flag;
                        for (int i = 0; ; i++) {
                            flag = false;
                            while (emitter.requested() == 0) {
                                if (!flag) {
                                    Log.i(TAG, "Oh no! I can't emit value!");
                                    flag = true;
                                }
                            }
                            emitter.onNext(i);
                            Log.i(TAG, "emit " + i + " , requested = " + emitter.requested());
                        }
                    }
                }, BackpressureStrategy.ERROR)    // 增加了一个参数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i(TAG, "onSubscribe: ");
                        s.request(96);  // 下游处理的能力   叶问说 我要打96个  // 96  当下游消费掉这96个 上游又开始发送128个
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
