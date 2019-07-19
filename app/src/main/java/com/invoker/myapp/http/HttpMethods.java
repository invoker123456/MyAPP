package com.invoker.myapp.http;

import com.invoker.myapp.bean.MsgBean;
import com.invoker.myapp.bean.MyJoke;
import com.invoker.myapp.interf.ApiService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by invoker on 2019-03-25
 * Description:
 */
public class HttpMethods {
    //    private static final String BASE_URL = "http://api.laifudao.com/open/";
    private static final String BASE_URL = "http://ws.lfsoft.net:9003";
    private static final int TIME_OUT = 4;
    private Retrofit retrofit;
    private ApiService apiService;

    private HttpMethods() {
        /**
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        /**
         * 由于retrofit底层的实现是通过okhttp实现的，所以可以通过okHttp来设置一些连接参数
         * 如超时等
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private static class sinalInstance {
        public static final HttpMethods instance = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return sinalInstance.instance;
    }

    public void getJoke(Observer<List<MyJoke>> observer) {
        apiService.getData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getMSG(Observer<MsgBean> observer, String ws, String intfc, String str) {
        apiService.getMsg(ws, intfc, str)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
