package com.zero.simple.recyler;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zero.library.utils.KLog;

/**
 * 作者： Wang
 * 时间： 2016/11/22
 */

public class EasyRefreshLayout extends SwipeRefreshLayout {
    public EasyRefreshLayout(Context context) {
        super(context);
    }

    public EasyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        KLog.i("requestDisallowInterceptTouchEvent---   "+b);
        super.requestDisallowInterceptTouchEvent(b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean bool = super.onInterceptTouchEvent(ev);
        KLog.i("onInterceptTouchEvent -----" + bool);
        return bool;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean bool = super.onTouchEvent(ev);
        KLog.i("-----" + bool);
        return bool;
    }
}
