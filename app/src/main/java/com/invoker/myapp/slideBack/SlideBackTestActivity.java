package com.invoker.myapp.slideBack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

public class SlideBackTestActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideback);

        // 是否启用滑动返回
        setSlideable(true);
        // 是否显示返回按钮
        showBackButton(true);

        findViewById(R.id.btn_click_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideBackTestActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
