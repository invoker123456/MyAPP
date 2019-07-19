package com.invoker.myapp.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.base.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by invoker on 2018-07-12
 * Description:
 */
public class RecyclerViewActivity extends MyBaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        ButterKnife.bind(this);

        adapter = new RecyclerViewAdapter(this, createData());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private List<Integer> createData() {
        List<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                data.add(R.mipmap.app);
            } else {
                data.add(R.mipmap.music_48);
            }
        }
        return data;
    }
}
