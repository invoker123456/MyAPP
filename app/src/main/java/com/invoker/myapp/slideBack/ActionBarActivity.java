package com.invoker.myapp.slideBack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.invoker.myapp.R;
import com.invoker.mylibrary.slideBack.SlideBackActivity;

/**
 * @author lihong
 * @since 2016/10/28
 */
public class ActionBarActivity extends SlideBackActivity {
    @Override
    public void setContentView(int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.action_bar_activity_layout, null);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.BELOW, R.id.title_bar_container);
        rootView.addView(view, 1, params);

        super.setContentView(rootView);

        String title = getTitle().toString();
        ((TextView) findViewById(R.id.title_text_center)).setText(title);
        findViewById(R.id.left_back_image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void showBackButton(boolean show) {
        findViewById(R.id.left_back_image_view).setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
