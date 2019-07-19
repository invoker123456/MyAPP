package com.invoker.myapp.main;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.FileReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by invoker on 2019-03-17
 * Description: flowable 解决上下游 一个很快一个很慢的问题
 * 读大文档  一行一行输出
 */
@RuntimePermissions
public class RxJava2_FlowableActivity extends MyBaseActivity {
    @BindView(R.id.tv_download)
    TextView tv_download;
    @BindView(R.id.tv_download_result)
    TextView mTvDownloadResult;

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        ButterKnife.bind(this);

        RxJava2_FlowableActivityPermissionsDispatcher.ApplySuccessWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RxJava2_FlowableActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 申请权限成功时
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void ApplySuccess() {
    }

    @OnClick({R.id.tv_download, R.id.tv_flowable})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
                test();
                break;
            case R.id.tv_flowable:
                break;
        }
    }

    public void test() {
        Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                        try {
                            String filepath = Environment.getDataDirectory().getPath();
                            Log.i(TAG, "subscribe: " + filepath);
                            FileReader reader = new FileReader("/storage/sdcard1/test1.txt");
                            BufferedReader br = new BufferedReader(reader);
                            String str;
                            while ((str = br.readLine()) != null && !emitter.isCancelled()) {
                                while (emitter.requested() == 0) {
                                    if (emitter.isCancelled()) {
                                        break;
                                    }
                                }
                                emitter.onNext(str);
                            }
                            br.close();
                            reader.close();

                            emitter.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                        mTvDownloadResult.setText(s);
                        try {
                            Thread.sleep(500);
                            mSubscription.request(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
