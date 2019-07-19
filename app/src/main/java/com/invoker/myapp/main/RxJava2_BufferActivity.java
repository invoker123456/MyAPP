package com.invoker.myapp.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by invoker on 2019-03-06
 * Description:   计算一段时间内数据的平均值  buffer
 */
public class RxJava2_BufferActivity extends MyBaseActivity {
    @BindView(R.id.tv_buffer)
    TextView mTv;

    private PublishSubject<Double> mPublishSubject;
    private CompositeDisposable mCompositeDisposable;
    private SourceHandler mSourceHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
        ButterKnife.bind(this);

        mPublishSubject = PublishSubject.create();
        DisposableObserver<List<Double>> disposableObserver = new DisposableObserver<List<Double>>() {
            @Override
            public void onNext(List<Double> o) {
                double result = 0;
                if (o.size() > 0) {
                    for (Double d : o) {
                        result += d;
                    }
                    result = result / o.size();
                }
                Log.i(TAG, "更新平均温度：" + result);
                mTv.setText("过去3秒收到了" + o.size() + "个数据， 平均温度为：" + result);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mPublishSubject
                .buffer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposableObserver);
        //开始测量温度。
        mSourceHandler = new SourceHandler();
        mSourceHandler.sendEmptyMessage(0);
    }

    public void updateTemperature(double temperature) {
        Log.i("RxJava2_BufferActivity", "温度测量结果：" + temperature);
        mPublishSubject.onNext(temperature);
    }

    private class SourceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            double temperature = Math.random() * 25 + 5;
            updateTemperature(temperature);
            //循环地发送。
            sendEmptyMessageDelayed(0, 250 + (long) (250 * Math.random()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSourceHandler.removeCallbacksAndMessages(null);
        mCompositeDisposable.clear();

    }
}
