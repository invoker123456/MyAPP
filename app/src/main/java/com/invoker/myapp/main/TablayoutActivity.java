package com.invoker.myapp.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.fragment.ChatFragment;
import com.invoker.myapp.fragment.ContactsFragment;
import com.invoker.myapp.fragment.FragmentAdapter;
import com.invoker.myapp.fragment.FriendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by invoker on 2018-07-11
 * Description:
 */
public class TablayoutActivity extends MyBaseActivity {

    @BindView(R.id.tab_layou)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private FragmentAdapter fragmentAdapter;
    //TabLayout标签
    private String[] titles = new String[]{"基本", "JAVA", "C#", "PHP","ANDROID", "JAVA", "C#", "PHP"};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        ButterKnife.bind(this);

        initView();

    }

    public void initView() {
        //设置TabLayout标签的显示方式  也可以在xml中定义
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //循环注入标签
        for (String tab : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(tab).setIcon(R.mipmap.music_48));
        }

        fragments.add(new ChatFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new FriendFragment());
        fragments.add(new ChatFragment());
        fragments.add(new ChatFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new FriendFragment());
        fragments.add(new ChatFragment());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在这里可以设置选中状态下  tab字体显示样式
                viewPager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(19);
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(14);
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
