package com.invoker.myapp.http;

import com.invoker.myapp.bean.Movie;
import com.invoker.myapp.bean.MsgBean;
import com.invoker.myapp.bean.NewsEntity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by invoker on 2019-03-18
 * Description: 封装线程管理和订阅
 */
public class ApiMethods {
    /**
     * 封装线程管理和订阅的过程
     */
    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     *
     * @param observer 由调用者传过来的观察者对象
     * @param start    起始位置
     * @param count    获取长度
     */
    public static void getTopMovie(Observer<Movie> observer, int start, int count) {
        ApiSubscribe(Api.getApiService().getTopMovie(start, count), observer);
    }

//    // 这是带 缓存策略 的
//    public static void getTopMovie(Observer<Movie> observer, int start, int count) {
//        ApiSubscribe(ApiStrategy.getApiService().getTopMovie(start, count), observer);
//    }

    // 获取数据
    public static void getMSG(String ws, String intfc, String jsonObjectString, Observer<MsgBean> observer) {
        ApiSubscribe(Api.getApiService().getMsg(ws, intfc, jsonObjectString), observer);
    }

    // 获取 干货
    public static void getNews(Observer<NewsEntity> observer, int page) {
        ApiSubscribe(Api.getApiService().getNews("福利", 10, page), observer);
    }
}
