package com.zero.simple;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.easy.utils.KLog;
import com.zero.simple.base.BaseActivity;
import com.zero.simple.view.EasyScrollView;

public class EasyScrollActivity extends BaseActivity implements View.OnClickListener {

    private EasyScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        scrollView = (EasyScrollView) findViewById(R.id.scroll_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
//                scrollView.
            }
        });

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(final float interpolatedTime, Transformation t) {
                KLog.i("--------- " + interpolatedTime);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollBy(10, (int) -(20f * interpolatedTime));
                    }
                });
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(2000);
//        scrollView.setAnimation(animation);
        findViewById(R.id.scroll_button_a).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scroll_button_a:
//                scrollView.scrollTo(20, -500);
                scrollView.zoomIn();
                break;
        }
    }
}
