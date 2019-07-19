package com.invoker.myapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.slideBack.SlideBackTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwoFragment extends LazyFragment {
    @BindView(R.id.fragment_two_btn)
    Button fragment_two_btn;

    private String tabName;
    private TextView mTabNameTv;
    private View mContainerView;

    public static TwoFragment newInstance(String tabTitle) {
        TwoFragment twoFragment = new TwoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tabName", tabTitle);
        twoFragment.setArguments(bundle);
        return twoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabName = getArguments().getString("tabName");
    }

    @OnClick({R.id.fragment_two_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_two_btn:
                startActivity(new Intent(TwoFragment.this.getActivity(), SlideBackTestActivity.class));
                break;
            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContainerView = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(this, mContainerView);
        return mContainerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabNameTv = (TextView) mContainerView.findViewById(R.id.id_fragment_two);
        mTabNameTv.setText(String.valueOf("布局2" + tabName));
        initNet();
    }

    @Override
    public void initNet() {

    }
}
