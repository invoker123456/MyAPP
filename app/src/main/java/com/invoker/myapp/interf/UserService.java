package com.invoker.myapp.interf;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface UserService {
    @POST
    public Call<ResponseBody> getText(@Url String url);

    //2.动态的url访问@PATH
    @POST("{username}")
    public Call<User> getUser(@Path("username") String username);
    //用于访问zhy的信息
    //http://192.168.1.102:8080/springmvc_users/user/zhy
    //用于访问lmj的信息
    //http://192.168.1.102:8080/springmvc_users/user/lmj

    //3.查询参数的设置@Query
    @POST("users")
    public Call<List<User>> getUserBySort(@Query("sortby") String sort);
    //http://baseurl/users?sortby=username
    //http://baseurl/users?sortby=id

    //4.POST请求体的方式向服务器传入json字符串@Body
    @POST("add")
    public Call<List<User>> addUser(@Body User user);

    //5.表单的方式传递键值对@FormUrlEncoded
    @POST("login")
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);

    //6.单文件上传@Multipart
    @Multipart
    @POST("register")
    Call<User> registerUser(@Part MultipartBody.Part photo, @Part("username") RequestBody username, @Part("password") RequestBody password);

    //7.多文件上传@PartMap
    @Multipart
    @POST("register")
    Call<User> registerUser2(@PartMap Map<String,RequestBody> params,@Part("username") RequestBody username, @Part("password") RequestBody password);

    //8.下载文件
    @POST("download")
    Call<ResponseBody> downloadTest();


    public class User {

    }
}
