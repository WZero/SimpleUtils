package com.zero.library.app;

import android.app.Application;

import com.zero.library.utils.ApplicationUtils;


/**
 * 作者： Wang
 * 时间： 2016/11/18
 */

public class RootApplication extends Application {
    @Override
    public void onCreate() {
        ApplicationUtils.init(this);
        super.onCreate();
    }
}
