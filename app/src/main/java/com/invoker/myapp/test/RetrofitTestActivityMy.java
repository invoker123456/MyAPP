package com.invoker.myapp.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.bean.Translation;
import com.invoker.myapp.bean.WeatherBean;
import com.invoker.myapp.interf.GetRequest_Interface;
import com.invoker.myapp.interf.IWeather;
import com.invoker.myapp.interf.UserService;
import com.invoker.myapp.slideBack.ActionBarActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTestActivityMy extends ActionBarActivity {
    @BindView(R.id.et_str)
    EditText et_str;
    @BindView(R.id.et_tgt)
    EditText et_tgt;
    @BindView(R.id.btn_trans)
    Button btn_trans;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.Retrofit_btn)
    Button Retrofit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        // 首页禁用滑动返回
        setSlideable(false);

//        request(); // 使用Retrofit封装的方法
        request_weather();
    }

    public void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        //步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对 发送请求 进行封装
        Call<Translation> call = request.getcall(et_str.getText().toString());

        // 步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
//                $Log(response.body().getTranslateResult().get(0).get(0).getTgt());
                et_tgt.setText(response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
//                $Log("请求失败");
//                $Log(t.getMessage());
            }
        });
    }

    public void request_weather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thinkpage.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        IWeather iWeather = retrofit.create(IWeather.class);
        Call<WeatherBean> call = iWeather.weather("rot2enzrehaztkdk", "beijing");
        call.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                WeatherBean weatherBean = response.body();
//                $Log("" + weatherBean.results.get(0).now.temperature);
                tv.setText(weatherBean.results.get(0).location.path + ":" + weatherBean.results.get(0).now.temperature);
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
//                $Log("error " + t.toString());
                tv.setText("error " + t.toString());
            }
        });
    }

    //2.动态的url访问@PATH
    public void request2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        UserService userService = retrofit.create(UserService.class);
        Call<UserService.User> call = userService.getUser("zhy");
        call.enqueue(new Callback<UserService.User>() {
            @Override
            public void onResponse(Call<UserService.User> call, Response<UserService.User> response) {
//                $Log(response.body().toString());
            }

            @Override
            public void onFailure(Call<UserService.User> call, Throwable t) {

            }
        });
    }

    //3.查询参数的设置@Query
    public void request3() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        UserService userService = retrofit.create(UserService.class);
        Call<List<UserService.User>> call = userService.getUserBySort("username");
        call.enqueue(new Callback<List<UserService.User>>() {
            @Override
            public void onResponse(Call<List<UserService.User>> call, Response<List<UserService.User>> response) {
//                $Log(response.body().toString());
            }

            @Override
            public void onFailure(Call<List<UserService.User>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.btn_trans, R.id.Retrofit_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_trans:
                request();
//                doGet();
                break;
            case R.id.Retrofit_btn:
                startActivity(new Intent(RetrofitTestActivityMy.this, Rxjava_imageActivityMy.class));
                break;
            default:
                break;
        }
    }
}
