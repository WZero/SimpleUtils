package com.zero.library.utils;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 作者： Wang
 * 时间： 2016/12/1
 */
public class ApplicationUtils {

    private static WeakReference<Application> weakReference;

    /**
     * 需要先初始化后才能使用
     *
     * @param application Application
     */
    public static void init(Application application) {
        weakReference = new WeakReference<>(application);
    }

    /**
     * @return Application
     */
    public static Application getApplication() {
        return weakReference.get();
    }

    /**
     * @return Context
     */
    public static Context getApplicationContext() {
        return weakReference.get().getApplicationContext();
    }
}
