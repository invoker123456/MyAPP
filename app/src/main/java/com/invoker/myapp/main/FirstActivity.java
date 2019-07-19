package com.invoker.myapp.main;

import android.os.Bundle;
import android.view.View;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.slideBack.SlideBackTestActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by invoker on 2018-06-20
 * Description: 用来指向各个activity
 */
public class FirstActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ButterKnife.bind(this);

        // 是否启用滑动返回
        setSlideable(true);
        // 是否显示返回按钮
        showBackButton(true);

//        showToast("测试");
        $Log("测试"); // TAG 默认当前的Activity的名称
    }

    @OnClick({R.id.first_btn_login, R.id.first_btn_fragment, R.id.first_btn_slideBack,
            R.id.first_btn_update, R.id.first_btn_loading, R.id.first_btn_picture,
            R.id.first_btn_tablayout, R.id.first_btn_recyclerView,
            R.id.first_btn_rxjava2,
            R.id.first_btn_rxjava_2, R.id.first_btn_rxjava2_News, R.id.first_btn_rxjava2_Flowable,
            R.id.first_btn_rxjava2_RxJava2AndRetrofit2, R.id.first_btn_rxjava2_FlatMap,
            R.id.first_btn_rxjava2_retrofit2_encapsulate,
            R.id.first_btn_mob, R.id.first_btn_vpn, R.id.first_btn_videoplayer})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_btn_login:
                NewActivity(LoginActivity.class);
                break;
            case R.id.first_btn_fragment:
                NewActivity(MainActivity.class);
                break;
            case R.id.first_btn_slideBack:
                NewActivity(SlideBackTestActivity.class);
                break;
            case R.id.first_btn_update:
                NewActivity(UpdateActivity.class);
                break;
            case R.id.first_btn_loading:
                NewActivity(LoadingActivity.class);
                break;
            case R.id.first_btn_picture:
                NewActivity(PictureActivity.class);
                break;
            case R.id.first_btn_tablayout:
                NewActivity(TablayoutActivity.class);
                break;
            case R.id.first_btn_recyclerView:
                NewActivity(RecyclerViewActivity.class);
                break;
            case R.id.first_btn_rxjava2:
                NewActivity(RxJava2Activity.class);
                break;
            case R.id.first_btn_rxjava_2:
                NewActivity(RxJava2_BufferActivity.class);
                break;
            case R.id.first_btn_rxjava2_News:
                NewActivity(RxJava2_NewsActivity.class);
                break;
            case R.id.first_btn_rxjava2_Flowable:
                NewActivity(RxJava2_FlowableActivity.class);
                break;
            case R.id.first_btn_rxjava2_RxJava2AndRetrofit2:
                NewActivity(RxJava2_Retrofit2Activity.class);
                break;
            case R.id.first_btn_rxjava2_FlatMap:
                NewActivity(RxJava2_FlatMapActivity.class);
                break;
            case R.id.first_btn_rxjava2_retrofit2_encapsulate:
                NewActivity(RxJava2_Retrofit2EncapsulateActivity.class);
                break;
            case R.id.first_btn_mob:
                NewActivity(MobTestActivity.class);
                break;
            case R.id.first_btn_vpn:
                NewActivity(Vpn_TestActivity.class);
                break;
            case R.id.first_btn_videoplayer:
                NewActivity(VideoPlayerActivity.class);
                break;
            default:
                break;
        }
    }
}
