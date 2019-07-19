package com.invoker.myapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.invoker.myapp.R;
import com.invoker.myapp.bean.ZhuangbiImage;
import com.invoker.myapp.interf.ApiService;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by invoker on 2018-06-11
 * Description:   RxJava2 基本
 */
public class ChatFragment extends Fragment {
    protected Disposable disposable;
    private static ApiService zhuangbiApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.chat_et)
    EditText chat_et;

    ZhuangbiListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.activity_tab_chat, container, false);
        ButterKnife.bind(this, chatView);

//        // 设置布局方式，单列显示，所以 后面设置为1
//        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new ZhuangbiListAdapter(getActivity(), R.layout.item_rv_search, null);
//        recyclerView.setAdapter(adapter);

        return chatView;
    }

    @OnClick({R.id.bt_refresh})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_refresh:
                search(chat_et.getText().toString());
                break;
        }
    }

    private void search(String key) {
        disposable = getZhuangbiApi()
                .search(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuangbiImage>>() {
                    @Override
                    public void accept(List<ZhuangbiImage> zhuangbiImages) throws Exception {
                        // 设置布局方式，单列显示，所以 后面设置为1
                        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ZhuangbiListAdapter(getActivity(), R.layout.item_rv_search, zhuangbiImages);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "数据加载失败!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static ApiService getZhuangbiApi() {
        if (zhuangbiApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.zhuangbi.info/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            zhuangbiApi = retrofit.create(ApiService.class);
        }
        return zhuangbiApi;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class ZhuangbiListAdapter extends CommonAdapter<ZhuangbiImage> {
        private Context mContext;
        private List<ZhuangbiImage> datas = new ArrayList<>();

        public ZhuangbiListAdapter(Context context, int layoutId, List<ZhuangbiImage> datas) {
            super(context, layoutId, datas);
            this.mContext = context;
            this.datas = datas;
        }

        @Override
        protected void convert(ViewHolder holder, ZhuangbiImage zhuangbiImage, int position) {
            holder.setText(R.id.descriptionTv, zhuangbiImage.description);
            Glide.with(mContext).load(zhuangbiImage.image_url).into((ImageView) holder.getView(R.id.imageIv));
        }
    }
}
