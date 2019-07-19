package com.invoker.myapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.invoker.myapp.R;
import com.invoker.myapp.test.RetrofitTestActivityMy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneFragment extends LazyFragment {
    @BindView(R.id.fragment_one_btn)
    Button fragment_one_btn;

    private View mContainerView;

    public static OneFragment newInstance() {
        OneFragment oneFragment = new OneFragment();
        return oneFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.fragment_one_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_one_btn:
                startActivity(new Intent(OneFragment.this.getActivity(), RetrofitTestActivityMy.class));
                break;
            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContainerView = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, mContainerView);
        return mContainerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initNet() {
    }
}
