package com.invoker.myapp.test;

import android.os.Bundle;
import android.widget.ImageView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.slideBack.ActionBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Rxjava_imageActivityMy extends ActionBarActivity {
    @BindView(R.id.rxjava_img)
    ImageView rxjava_img;

    int[] img = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_image);
        ButterKnife.bind(this);

        rxjava();
    }

    public void rxjava() {
        Observable
                .create(new ObservableOnSubscribe() {
                    @Override
                    public void subscribe(ObservableEmitter e) throws Exception {
                        while (true) {
                            for (int i : img) {
                                try {
                                    Thread.sleep(500);
                                    e.onNext(i);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function() {
                    @Override
                    public Integer apply(Object o) throws Exception {
                        Integer a = Integer.parseInt(o.toString());
//                        $Log("" + a);
                        return a;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
//                        $Log(value.toString());
                        rxjava_img.setImageDrawable(getResources().getDrawable(Integer.parseInt(value.toString())));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
