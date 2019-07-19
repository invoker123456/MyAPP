package com.invoker.myapp.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.bean.JcssBean;
import com.invoker.myapp.bean.JyddBean;
import com.invoker.myapp.bean.MsgBean;
import com.invoker.myapp.bean.MyJoke;
import com.invoker.myapp.bean.VideoTest;
import com.invoker.myapp.http.ApiMethods;
import com.invoker.myapp.http.HttpMethods;
import com.invoker.myapp.interf.ApiService;
import com.invoker.myapp.observer.ObserverOnNextListener;
import com.invoker.myapp.progress.ProgressObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.invoker.myapp.base.MyApp.getObjectList;

/**
 * Created by invoker on 2019-03-23
 * Description:  RxJava2 - flatMap 实现嵌套请求
 */
public class RxJava2_FlatMapActivity extends MyBaseActivity {
    @BindView(R.id.tv_start)
    TextView tv_start;
    @BindView(R.id.tv_show)
    TextView tv_show;
    @BindView(R.id.tv_show2)
    TextView tv_show2;

    private Context context;

//    // 定义Observable接口类型的网络请求对象
//    Observable<Translation1> observable1;
//    Observable<Translation2> observable2;
//
//    // 定义Observable接口类型的网络请求对象
//    Observable<MsgBean> observableNew1;
//    Observable<MsgBean> observableNew2;
//
//    String jcssId, ssflId, shqkId1, shqkId2, bzsmId;
//    String[] jcsss = null;    // 基础设施s
//    List<JcssBean> jcssArray = new ArrayList<>();  // 基础设施s
//    String[] ssfls = null;    // 基础设施 对应的 bzsm   用于获取损坏情况
//    List<JcssBean> ssflArray = new ArrayList<>();  // 基础设施 对应的 bzsm
//    private List<JcssBean> ssflBeanList = new ArrayList<>();
//    private List<JcssBean> ssflNewArray = new ArrayList<>();  // 基础设施
//
//    private Map<Integer, VideoTest> map = new HashMap<>();
//    private int index = 0;
//
//    private Map<Integer, JcssBean> jcssMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2_retrofit2);
        ButterKnife.bind(this);
        context = this;

    }

//    public void testFlatMap() {
//        // 步骤1：创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
//                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
//                .build();
//        // 步骤2：创建 网络请求接口 的实例
//        ApiService request = retrofit.create(ApiService.class);
//        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
//        observable1 = request.getCall();
//        observable2 = request.getCall_2();
//
//        observable1.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<Translation1>() {
//                    @Override
//                    public void accept(Translation1 result) throws Exception {
//                        Log.i(TAG, "accept: 第1次网络请求成功");
//                        result.show();
//                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
//                    }
//                })
//                // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
//                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
//                // 但对于初始观察者，它则是新的被观察者
//                .observeOn(Schedulers.io())
//                .flatMap(new Function<Translation1, ObservableSource<Translation2>>() {
//                    @Override
//                    public ObservableSource<Translation2> apply(Translation1 translation1) throws Exception {
//                        // 将网络请求1转换成网络请求2，即发送网络请求2
//                        return observable2;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread()) // （初始观察者）切换到主线程 处理网络请求2的结果
//                .subscribe(new Consumer<Translation2>() {
//                    @Override
//                    public void accept(Translation2 translation2) throws Exception {
//                        Log.i(TAG, "accept: 第2次网络请求成功");
//                        translation2.show();
//                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        showToast("登录失败");
//                    }
//                });
//    }

    @OnClick({R.id.tv_start, R.id.tv_show, R.id.tv_show2})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
//                getDJ();
//                getJydd();
                getJYDD();
//                test();
//                test2();
//                newTest();
                break;
//            case R.id.tv_show:
//                ShowChoiseJcss();
//                break;
//            case R.id.tv_show2:
//                // 根据jcssId 获取 ssfls 和 ssflArray
//                ssfls = null;
//                ssflNewArray = new ArrayList<>();
//                try {
//                    for (int i = 0; i < ssflBeanList.size(); i++) {
//                        if (jcssId.equals(ssflBeanList.get(i).FL)) {
//                            ssflNewArray.add(ssflBeanList.get(i));
//                        }
//                    }
//                    ssfls = new String[ssflNewArray.size()];
//                    for (int i = 0; i < ssflNewArray.size(); i++) {
//                        ssfls[i] = ssflNewArray.get(i).MC;
//                    }
//                    ShowChoiseSsfl();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
        }
    }

    public void getDJ() {
        HttpMethods.getInstance().getJoke(new Observer<List<MyJoke>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(List<MyJoke> myJokes) {
                Log.i(TAG, "onNext: " + myJokes.size());
                tv_show.setText(myJokes.get(0).getContent());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
                d.dispose();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
                d.dispose();
            }
        });
    }

    public void getJydd() {
        JsonObject jsonObject = new JsonObject();
        HttpMethods.getInstance().getMSG(new Observer<MsgBean>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(MsgBean msgBean) {
                Log.i(TAG, "onNext: ");
                Gson gson = new Gson();
                if (Integer.valueOf(msgBean.getCode()) > 0) {
                    String json = gson.toJson(msgBean.data);
                    List<JyddBean> lcgzData = getObjectList(json, JyddBean.class);
                    tv_show.setText(lcgzData.get(0).GYSMC);
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                d.dispose();
                Log.i(TAG, "onComplete: ");
            }
        }, "8", "SQL_JYSQJYDD", jsonObject.toString());
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
                        tv_show.setText(lcgzData.get(0).ID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        JsonObject jsonObject = new JsonObject();
        ApiMethods.getMSG("8", "SQL_JYSQJYDD", jsonObject.toString(), new ProgressObserver<MsgBean>(this, listener));
    }

//    public void test2() {
//        // 步骤1：创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://ws.lfsoft.net:9003") // 设置 网络请求 Url
//                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
//                .build();
//        // 步骤2：创建 网络请求接口 的实例
//        final ApiService request = retrofit.create(ApiService.class);
//        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
//        JsonObject jsonObject1 = new JsonObject();
//        observableNew1 = request.getMsg("8", "SQL_SZWHJCSS", jsonObject1.toString());
//
//        observableNew1.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<MsgBean>() {
//                    @Override
//                    public void accept(MsgBean msgBean) throws Exception {
//                        Log.i(TAG, "accept: 第1次网络请求成功" + msgBean.msg);
//                        try {
//                            Gson gson = new Gson();
//                            if (Integer.valueOf(msgBean.getCode()) > 0) {
//                                String json = gson.toJson(msgBean.data);
//                                jcssArray = getObjectList(json, JcssBean.class);
//                                jcsss = new String[jcssArray.size()];
//                                for (int i = 0; i < jcssArray.size(); i++) {
//                                    jcsss[i] = jcssArray.get(i).MC;
//                                }
//                                jcssId = jcssArray.get(0).FID2;
//                                tv_show.setText(jcssArray.get(0).MC + ",fid2:" + jcssArray.get(0).FID2 + ",id:" + jcssArray.get(0).ID);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                })
//                .flatMap(new Function<MsgBean, Observable<JcssBean>>() {
//                    @Override
//                    public Observable<JcssBean> apply(MsgBean msgBean1) throws Exception {
//                        List<JcssBean> jcssBeans = null;
//                        Gson gson = new Gson();
//                        if (Integer.valueOf(msgBean1.getCode()) > 0) {
//                            String json = gson.toJson(msgBean1.data);
//                            jcssBeans = getObjectList(json, JcssBean.class);
//                        }
//                        return Observable.fromIterable(jcssBeans);
//                    }
//                })
//                .map(new Function<JcssBean, String>() {
//                    @Override
//                    public String apply(JcssBean jcssBean) throws Exception {
//                        return jcssBean.FID2;
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .flatMap(new Function<String, ObservableSource<MsgBean>>() {
//                    @Override
//                    public ObservableSource<MsgBean> apply(String str) throws Exception {
//                        Log.i(TAG, "apply: 第2次网络请求: " + str);
//                        JsonObject jsonObject1 = new JsonObject();
//                        jsonObject1.addProperty("fid2", str);
//                        observableNew2 = request.getMsg("8", "SQL_SZWHSSFL", jsonObject1.toString());
//                        return observableNew2;
//                    }
//                })
//                .map(new Function<MsgBean, List<JcssBean>>() {
//                    @Override
//                    public List<JcssBean> apply(MsgBean msgBean) throws Exception {
//                        Log.i(TAG, "apply: 第2次网络请求成功");
//                        try {
//                            Gson gson = new Gson();
//                            String json = gson.toJson(msgBean.data);
//                            ssflArray = getObjectList(json, JcssBean.class);
//                            ssflBeanList.addAll(ssflArray);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return ssflBeanList;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<JcssBean>>() {
//                    @Override
//                    public void accept(List<JcssBean> jcssBean) throws Exception {
//                        Log.i(TAG, "accept: 完成" + jcssBean.size());
//                        tv_show2.setText(jcssBean.get(0).MC + ",fl:" + jcssBean.get(0).FL + ",id:" + jcssBean.get(0).ID);
////                        for (JcssBean v : jcssBean) {
////                            Log.i(TAG, v.MC + ",fl:" + v.FL + ",id:" + v.ID);
////                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.i(TAG, "accept: " + throwable);
//                        showToast("失败");
//                    }
//                });
//    }

//    public void newTest() {
//        List<VideoTest> videoTests = new ArrayList<>();
//
//        for (int i = 0; i < 6; i++) {
//            VideoTest videoTest = new VideoTest("qwert" + i, "http:www.dddd.com/" + i);
//            videoTests.add(videoTest);
//        }
//        Observable.fromArray(videoTests)//这里模拟请求数据集合
//                .flatMap(new Function<List<VideoTest>, ObservableSource<VideoTest>>() {
//                    @Override
//                    public ObservableSource<VideoTest> apply(@NonNull List<VideoTest> pVideoTests) throws Exception {
//                        index = 0;
//                        return Observable.fromIterable(pVideoTests);// 这里的fromIterable是一个一个发送数据
//                    }
//                })
//                //单独处理每个数据
//                .map(new Function<VideoTest, String>() {
//                    @Override
//                    public String apply(@NonNull VideoTest pVideoTest) throws Exception {
//                        //这里需要使用map来绑定对象跟key key可以使用integer
//                        map.put(index, pVideoTest);
//                        return pVideoTest.getUrl();
//                    }
//                })
//                .map(new Function<String, String>() {
//                    @Override
//                    public String apply(@NonNull String ps) throws Exception {
//                        //这边处理url 应该是请求接口B 我这就简单点处理了
//                        return ps + "处理过的";
//                    }
//                })
//                .map(new Function<String, VideoTest>() {
//                    @Override
//                    public VideoTest apply(@NonNull String pS) throws Exception {
//                        //这边应该将正确的url赋值给对应的对象
//                        VideoTest videoTest = map.get(index);
//                        videoTest.setUrl(pS);
//                        index++;
//                        return videoTest;
//                    }
//                })
//                //这个操作符是用来收集对象的
//                .collect(new Callable<List<VideoTest>>() {
//                    @Override
//                    public List<VideoTest> call() throws Exception {
//                        return new ArrayList<>();
//                    }
//                }, new BiConsumer<List<VideoTest>, VideoTest>() {
//                    @Override
//                    public void accept(List<VideoTest> pNewses, VideoTest pNews) throws Exception {
//                        pNewses.add(pNews);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<VideoTest>>() {
//                    @Override
//                    public void accept(List<VideoTest> pVideoTests) throws Exception {
//                        for (VideoTest v : pVideoTests) {
//                            Log.i(TAG, v.getId() + "," + v.getUrl());
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable pThrowable) throws Exception {
//                        Log.i(TAG, "accept: " + pThrowable);
//                    }
//                });
//    }
//
//    //  弹出基础设施 对话框
//    private void ShowChoiseJcss() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setItems(jcsss, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // 先清空之前选的内容
//                tv_show.setText(jcsss[which]);
//                jcssId = jcssArray.get(which).FID2; //  灯类: 0->306
//
//            }
//        });
//        builder.show();
//    }
//
//    //  弹出基础设施 对话框
//    private void ShowChoiseSsfl() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setItems(ssfls, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // 先清空之前选的内容
//                tv_show2.setText(ssfls[which]);
//                ssflId = ssflNewArray.get(which).ID; //  灯类: 0->306
//
//            }
//        });
//        builder.show();
//    }
}
