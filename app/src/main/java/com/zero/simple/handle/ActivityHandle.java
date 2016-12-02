package com.zero.simple.handle;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 弱应用的 Handle
 * 作者： Wang
 * 时间： 2016/12/1
 */
public class ActivityHandle extends Handler {

    private WeakReference<Activity> activityWeakReference;

    public ActivityHandle(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (activityWeakReference.get() != null) {

        }
    }
}
