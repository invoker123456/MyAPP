package com.invoker.myapp.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.invoker.myapp.R;
import com.invoker.myapp.base.MyApp;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.bean.JyddBean;
import com.invoker.myapp.bean.MsgBean;
import com.invoker.myapp.bean.NewsEntity;
import com.invoker.myapp.bean.NewsResultEntity;
import com.invoker.myapp.bean.User;
import com.invoker.myapp.db.DaoSession;
import com.invoker.myapp.db.UserDao;
import com.invoker.myapp.http.ApiMethods;
import com.invoker.myapp.observer.ObserverOnNextListener;
import com.invoker.myapp.progress.ProgressObserver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.invoker.myapp.base.MyApp.getObjectList;

/**
 * Created by invoker on 2019-04-05
 * Description:
 */
public class RxJava2_Retrofit2EncapsulateActivity extends MyBaseActivity {
    @BindView(R.id.tv_download)
    TextView tv_download;
    @BindView(R.id.tv_download_result)
    TextView mTvDownloadResult;
    @BindView(R.id.result2)
    TextView result2;

//    MyApp myApp = (MyApp) getApplication();
    DaoSession daoSession = MyApp.getDaoSession();
    UserDao userDao = daoSession.getUserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        ButterKnife.bind(this);

        new dbThread().start();
    }

    @OnClick({R.id.tv_download, R.id.tv_flowable})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
//                getJYDD();
                getNews();
                break;
            case R.id.tv_flowable:
                result2.setText(query2().getName());
                break;
        }
    }

    public class dbThread extends Thread{
        @Override
        public void run() {
            super.run();
            // 增
            User user = new User();
            user.setId(1);
            user.setName("小明");
            user.setAge(16);

            userDao.insertOrReplace(user);
        }
    }

    public User query2(){
        return userDao.loadByRowId(1);//根据ID查询
    }

    public void getJYDD() {
        ObserverOnNextListener<MsgBean> listener = new ObserverOnNextListener<MsgBean>() {
            @Override
            public void onNext(MsgBean msgBean) {
                try {
                    Gson gson = new Gson();
                    if (Integer.valueOf(msgBean.getCode()) > 0) {
                        String json = gson.toJson(msgBean.data);
                        List<JyddBean> lcgzData = getObjectList(json, JyddBean.class);
                        mTvDownloadResult.setText(lcgzData.get(0).ID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        JsonObject jsonObject = new JsonObject();
        ApiMethods.getMSG("8", "SQL_JYSQJYDDa", jsonObject.toString(), new ProgressObserver<MsgBean>(this, listener));
    }

    public void getNews() {
        ObserverOnNextListener<NewsEntity> listener = new ObserverOnNextListener<NewsEntity>() {
            @Override
            public void onNext(NewsEntity msgBean) {
                try {
                    Gson gson = new Gson();
                    String json = gson.toJson(msgBean.getResults());
                    List<NewsResultEntity> lcgzData = getObjectList(json, NewsResultEntity.class);
                    mTvDownloadResult.setText(lcgzData.get(0).getDesc());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        JsonObject jsonObject = new JsonObject();
        ApiMethods.getNews(new ProgressObserver<NewsEntity>(this, listener), 1);
    }
}
