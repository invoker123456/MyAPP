package com.invoker.mylibrary.base;

import android.app.Application;
import android.content.Context;

import com.orhanobut.hawk.Hawk;

/**
 * Created by invoker on 2018-05-10
 * Description:
 */
public class BaseApplication extends Application {

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        Hawk.init(this).build();
    }
}
