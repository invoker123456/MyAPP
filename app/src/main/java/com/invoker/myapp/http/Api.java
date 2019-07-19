package com.invoker.myapp.http;

import com.invoker.myapp.interf.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by invoker on 2019-03-18
 * Description:  封装Retrofit请求过程
 */
public class Api {
//    public static String baseUrl = "https://api.douban.com/v2/movie/";

//    public static String baseUrl = "http://www.lfsoft.net:9003";
    public static String baseUrl = "http://gank.io";
    public static ApiService apiService;

    // 单例
    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    private Api() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                //适配RxJava2.0,RxJava1.x则为RxJavaCallAdapterFactory.create()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }
}
