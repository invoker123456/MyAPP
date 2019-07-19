package com.invoker.myapp.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by UX501 on 2018-03-21.
 */

public class TestActivityMy extends MyBaseActivity {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_trans)
    Button btn_trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
//        new AlertDialog.Builder(this).setTitle("自定义布局")
//                .setView(layout)
//                .setPositiveButton("确定", null)
//                .setNegativeButton("取消", null)
//                .show();

//        Flowable.just("Hello world").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                $Log(s);
//            }
//        });


        Observable
                .create(new ObservableOnSubscribe() {
                    @Override
                    public void subscribe(ObservableEmitter emitter) throws Exception {
                        emitter.onNext("Hello");
                        emitter.onNext("world");
                        emitter.onComplete();
                    }
                })
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        $Log("onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        $Log("" + o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        $Log("onError");
                    }

                    @Override
                    public void onComplete() {
                        $Log("onComplete");
                    }
                });


        /**
         * 1p
         * RxJava
         */
//        Observable
//                .create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                        Log.d(TAG, "emit 1p");
//                        emitter.onNext(1p);
//                        Log.d(TAG, "emit 2");
//                        emitter.onNext(2);
//                        Log.d(TAG, "emit 3");
//                        emitter.onNext(3);
//                        Log.d(TAG, "emit complete");
//                        emitter.onComplete();
//                        Log.d(TAG, "emit 4");
//                        emitter.onNext(4);
//                    }
//                })
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.i(TAG, "onNext: " + integer);
//                    }
//                });


        /**
         * 2
         * RxJava 线程切换  Schedulers
         */
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                $Log("Observable thread is : " + Thread.currentThread().getName());
//                $Log("emit 1p");
//                emitter.onNext(1p);
//            }
//        });
//        Consumer<Integer> consumer = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                $Log("Observer thread is :" + Thread.currentThread().getName());
//                $Log("onNext: " + integer);
//            }
//        };
//        observable
//                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        $Log("After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        $Log("After observeOn(io), current thread is : " + Thread.currentThread().getName());
//                    }
//                })
//                .subscribe(consumer);

        /**
         * 3
         * Rxjava 变换操作符map
         */
//        Observable
//                .create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> e) {
//                        e.onNext(1p);
//                        e.onNext(2);
//                        e.onNext(3);
//                    }
//                })
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) {
//                        return "this is result " + integer;
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        $Log(s);
//                    }
//                });

        /**
         * 3
         * Rxjava 变换操作符flatMap (不一定按照上游发送的顺序来发送)
         *        concatMap和flatMap 一样 不过是按照顺序发送的
         */
//        Observable
//                .create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> e) {
//                        e.onNext(1p);
//                        e.onNext(2);
//                        e.onNext(3);
//                    }
//                })
//                .flatMap(new Function<Integer, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Integer integer) throws Exception {
//                        final List<String> list = new ArrayList<>();
//                        for (int i = 0; i < 3; i++) {
//                            list.add("i am value " + integer);
//                        }
//                        return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        $Log(s);
//                    }
//                });

    }

    @OnClick({R.id.btn_trans, R.id.tv})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                $Log(TAG + " tv");
                break;
            case R.id.btn_trans:
                showToast(TAG);

                $Log(TAG + " btn");
                break;
            default:
                break;
        }
    }
}
