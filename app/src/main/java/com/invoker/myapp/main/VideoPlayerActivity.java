package com.invoker.myapp.main;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by invoker on 2019-07-15
 * Description:
 */
public class VideoPlayerActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        ButterKnife.bind(this);

        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
//        jzVideoPlayerStandard.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4",
//                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
//                "饺子闭眼睛");
        jzVideoPlayerStandard.setUp("http://www.lfsoft.net:9020/video/T_SPZL/1.spnr",
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "饺子闭眼睛");
        Glide.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideoPlayerStandard.thumbImageView);

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
