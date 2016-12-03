package com.zero.simple;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.easy.utils.KLog;
import com.zero.simple.base.BaseActivity;

/**
 * 自定义View 实现回弹
 */
public class SpringBackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_springback);
       RelativeLayout layout= (RelativeLayout) findViewById(R.id.activity_springback);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i("-----------setOnClickListener");
            }
        });
    }
}
