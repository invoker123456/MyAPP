package com.invoker.myapp.interf;


import com.invoker.myapp.bean.BookBean;
import com.invoker.myapp.bean.JyddBean;
import com.invoker.myapp.bean.Movie;
import com.invoker.myapp.bean.MsgBean;
import com.invoker.myapp.bean.MyJoke;
import com.invoker.myapp.bean.NewsEntity;
import com.invoker.myapp.bean.RydjBean;
import com.invoker.myapp.bean.RyxhBean;
import com.invoker.myapp.bean.Top250Bean;
import com.invoker.myapp.bean.ZhuangbiImage;
import com.invoker.myapp.main.RxJava2_FlatMapActivity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by allen on 2016/12/26.
 */

public interface ApiService {

    @GET("v2/book/1220562")
    Observable<BookBean> getBook();

    @GET("v2/movie/top250")
    Observable<Top250Bean> getTop250(@Query("count") int count);

    @GET("v2/book/1220562")
    Observable<String> getBookString();

    @POST("plug-in/ws/WSClient.js.jsp")
    Observable<String> getData(@Query("intfc") String intfc, @Query("jsonObjectString") String jsonObjectString);

    //  干货
    @GET("api/data/{category}/{count}/{page}")
    Observable<NewsEntity> getNews(@Path("category") String category, @Path("count") int count, @Path("page") int page);

    // 装逼大全  https://www.zhuangbi.info/search?q=装逼
    @GET("search")
    Observable<List<ZhuangbiImage>> search(@Query("q") String query);

    // 豆瓣 top250
    @GET("top250")
    Observable<Movie> getTopMovie(@Query("start") int start, @Query("count") int count);

//    // 网络请求1
//    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
//    Observable<RxJava2_FlatMapActivity.Translation1> getCall();
//
//    // 网络请求2
//    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
//    Observable<RxJava2_FlatMapActivity.Translation2> getCall_2();

    // 获取数据
    @FormUrlEncoded
    @POST("plug-in/ws/WSClient.js.jsp")
    Observable<MsgBean> getMsg(@Field("ws") String ws, @Field("intfc") String intfc, @Field("jsonObjectString") String jsonObjectString);

    // 获取加油申请 -加油地点(sql 8)
    @FormUrlEncoded
    @POST("plug-in/ws/WSClient.js.jsp")
    Observable<JyddBean> getJydd(@Field("ws") String ws, @Field("intfc") String intfc, @Field("jsonObjectString") String jsonObjectString);

    // 获取加油申请
    @FormUrlEncoded
    @POST("plug-in/ws/WSClient.js.jsp")
    Observable<RyxhBean> getRyxh(@Field("ws") String ws, @Field("intfc") String intfc, @Field("jsonObjectString") String jsonObjectString);

    // 获取加油申请
    @FormUrlEncoded
    @POST("plug-in/ws/WSClient.js.jsp")
    Observable<RydjBean> getRydj(@Field("ws") String ws, @Field("intfc") String intfc, @Field("jsonObjectString") String jsonObjectString);

    /**
     * 来福笑话接口
     *
     * @return List<>
     */
    @GET("xiaohua.json")
    Observable<List<MyJoke>> getData();
}
