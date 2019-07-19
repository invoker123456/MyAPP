package com.invoker.myapp.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.bean.Movie;
import com.invoker.myapp.bean.Subjects;
import com.invoker.myapp.http.ApiMethods;
import com.invoker.myapp.interf.ApiService;
import com.invoker.myapp.observer.MyObserver;
import com.invoker.myapp.observer.ObserverOnNextListener;
import com.invoker.myapp.progress.ProgressObserver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by invoker on 2019-03-18
 * Description:  如何 优雅的封装 Retrofit2 + RxJava2
 */
public class RxJava2_Retrofit2Activity extends MyBaseActivity {
    @BindView(R.id.tv_start)
    TextView tv_start;
    @BindView(R.id.tv_show)
    TextView tv_show;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2_retrofit2);
        ButterKnife.bind(this);
        context = this;

    }

    @OnClick({R.id.tv_start})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                getMovie();
//                getMovie3();
                break;
        }
    }

    // 基本使用
    public void getMovie() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create()) //请求结果转换为基本类型
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Movie movieEntity) {
                        Log.i(TAG, "onNext: " + movieEntity.getTitle());
                        List<Subjects> list = movieEntity.getSubjects();
                        String str = "";
                        for (Subjects sub : list) {
                            Log.i(TAG, "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                            str = str + sub.getId() + "," + sub.getYear() + "," + sub.getTitle() + "\n";
                        }
                        tv_show.setText(str);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    // 封装1
    public void getMovie1() {
        Observer<Movie> observer = new Observer<Movie>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Movie movie) {
                Log.i(TAG, "onNext: " + movie.getTitle());
                List<Subjects> list = movie.getSubjects();
                String str = "";
                for (Subjects sub : list) {
                    Log.i(TAG, "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                    str = str + sub.getId() + "," + sub.getYear() + "," + sub.getTitle() + "\n";
                }
                tv_show.setText(str);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: Over!");
            }
        };
        ApiMethods.getTopMovie(observer, 0, 10);
    }

    // 封装2
    public void getMovie2() {
        ObserverOnNextListener<Movie> listener = new ObserverOnNextListener<Movie>() {
            @Override
            public void onNext(Movie movie) {
                Log.i(TAG, "onNext: " + movie.getTitle());
                List<Subjects> list = movie.getSubjects();
                String str = "";
                for (Subjects sub : list) {
                    Log.i(TAG, "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                    str = str + sub.getId() + "," + sub.getYear() + "," + sub.getTitle() + "\n";
                }
                tv_show.setText(str);
            }
        };
        ApiMethods.getTopMovie(new MyObserver<Movie>(this, listener), 0, 10);
    }

    // 封装3
    public void getMovie3() {
        ObserverOnNextListener<Movie> listener = new ObserverOnNextListener<Movie>() {
            @Override
            public void onNext(Movie movie) {
                Log.i(TAG, "onNext: " + movie.getTitle());
                List<Subjects> list = movie.getSubjects();
                String str = "";
                for (Subjects sub : list) {
                    Log.i(TAG, "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                    str = str + sub.getId() + "," + sub.getYear() + "," + sub.getTitle() + "\n";
                }
                tv_show.setText(str);
            }
        };
        ApiMethods.getTopMovie(new ProgressObserver<Movie>(this, listener), 0, 10);
    }

    // 封装4  带缓存  ApiStrategy
    public void getMovie4() {
        ObserverOnNextListener<Movie> listener = new ObserverOnNextListener<Movie>() {
            @Override
            public void onNext(Movie movie) {
                Log.i(TAG, "onNext: " + movie.getTitle());
                List<Subjects> list = movie.getSubjects();
                String str = "";
                for (Subjects sub : list) {
                    Log.i(TAG, "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                    str = str + sub.getId() + "," + sub.getYear() + "," + sub.getTitle() + "\n";
                }
                tv_show.setText(str);
            }
        };
        ApiMethods.getTopMovie(new ProgressObserver<Movie>(this, listener), 0, 10);
    }
}
