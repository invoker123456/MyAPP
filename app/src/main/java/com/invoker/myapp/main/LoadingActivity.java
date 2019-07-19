package com.invoker.myapp.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * Created by invoker on 2018-06-12
 * Description:
 */
public class LoadingActivity extends MyBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ZLoadingDialog dialog = new ZLoadingDialog(LoadingActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.PAC_MAN)//设置类型
                .setLoadingColor(Color.GREEN)//颜色
                .setHintText("Loading...")
                .setHintTextSize(14)
                //.setCanceledOnTouchOutside(false)
                //.setCancelable(false)
                .show();

    }
}
