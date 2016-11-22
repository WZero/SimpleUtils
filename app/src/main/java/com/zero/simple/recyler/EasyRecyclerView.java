package com.zero.simple.recyler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zero.library.utils.KLog;

/**
 * 作者： Wang
 * 时间： 2016/11/22
 */

public class EasyRecyclerView extends RecyclerView {
    public EasyRecyclerView(Context context) {
        super(context);
    }

    public EasyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean bool = super.onInterceptTouchEvent(ev);
        KLog.i("EasyRecyclerView---onInterceptTouchEvent -----" + bool);
        return bool;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean bool = super.onTouchEvent(ev);
        KLog.i("EasyRecyclerView-----" + bool);
        return bool;
    }
}
