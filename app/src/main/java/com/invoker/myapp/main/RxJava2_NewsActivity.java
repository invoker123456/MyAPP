package com.invoker.myapp.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.bean.NewsEntity;
import com.invoker.myapp.bean.NewsResultEntity;
import com.invoker.myapp.interf.ApiService;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by invoker on 2019-03-08
 * Description:
 */
public class RxJava2_NewsActivity extends MyBaseActivity {
    public Context context;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

//    @BindView(R.id.item_im)
//    ImageView imageView;

    private int mCurrentPage = 1;
    private NewsAdapter mNewsAdapter;
    private List<NewsResultEntity> mNewsResultEntities = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Observable<List<NewsResultEntity>> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2_news);
        context = this;
        ButterKnife.bind(this);

        initView();
        refreshArticle(++mCurrentPage);
    }

    @OnClick({R.id.bt_refresh})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_refresh:
                refreshArticle(++mCurrentPage);
                break;
        }
    }

    // 初始化 加载 
    private void initView() {
        try {
            // 设置布局方式，单列显示，所以 后面设置为1
            final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            layoutManager.setOrientation(RecyclerView.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);
            mNewsAdapter = new NewsAdapter(context, R.layout.item_rv_search, mNewsResultEntities);
            recyclerView.setAdapter(mNewsAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshArticle(final int page) {
        // flatMap / concatMap  : 整合后一起 返回  (concatMap:按顺序的)
        observable = Observable
                .just(page)
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<Integer, ObservableSource<List<NewsResultEntity>>>() {
                    @Override
                    public ObservableSource<List<NewsResultEntity>> apply(Integer integer) throws Exception {
//                        Observable<NewsEntity> androidNews = getObservable("Android", page);
//                        Observable<NewsEntity> iosNews = getObservable("iOS", page);
                        Observable<NewsEntity> androidNews = getObservable("福利", page);
                        Observable<NewsEntity> iosNews = getObservable("福利", page);

                        return Observable.zip(androidNews, iosNews, new BiFunction<NewsEntity, NewsEntity, List<NewsResultEntity>>() {
                            @Override
                            public List<NewsResultEntity> apply(NewsEntity androidEntity, NewsEntity iosEntity) throws Exception {
                                List<NewsResultEntity> result = new ArrayList<>();
                                result.addAll(androidEntity.getResults());
//                                result.addAll(iosEntity.getResults());
                                Log.i(TAG, "apply: " + result.size());
                                return result;
                            }
                        });
                    }
                });

        DisposableObserver<List<NewsResultEntity>> disposable = new DisposableObserver<List<NewsResultEntity>>() {
            @Override
            public void onNext(List<NewsResultEntity> value) {
                mNewsResultEntities.clear();
                mNewsResultEntities.addAll(value);
                mNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(disposable);
        mCompositeDisposable.add(disposable);
    }

    private Observable<NewsEntity> getObservable(String category, int page) {
        ApiService api = new Retrofit.Builder()
                .baseUrl("http://gank.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiService.class);
        return api.getNews(category, 10, page);
    }

    public class NewsAdapter extends CommonAdapter<NewsResultEntity> {
        private Context mContext;
        private List<NewsResultEntity> datas = new ArrayList<>();

        public NewsAdapter(Context context, int layoutId, List<NewsResultEntity> datas) {
            super(context, layoutId, datas);
            this.mContext = context;
            this.datas = datas;
        }

        @Override
        protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, NewsResultEntity newsResultEntity, int position) {
            holder.setText(R.id.descriptionTv, newsResultEntity.getDesc());
            Glide.with(mContext).load(newsResultEntity.getUrl()).into((ImageView) holder.getView(R.id.imageIv));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
