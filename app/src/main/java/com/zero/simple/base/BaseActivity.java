package com.zero.simple.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 作者： Wang
 * 时间： 2016/11/18
 */

public class BaseActivity extends RootActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =  getIntent();
        String title = intent.getStringExtra("title");
        if (title != null) {
            setTitle(title);
        }
    }
}
