package com.invoker.myapp.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by invoker on 2019-07-18
 * Description:
 */
public class WXShareActivity extends MyBaseActivity {

    @BindView(R.id.wxshare_tv_share)
    TextView wxshare_tv_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxshare);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.wxshare_tv_share})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.wxshare_tv_share:
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }
}
