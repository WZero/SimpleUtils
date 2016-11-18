package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.zero.library.utils.DensityUtils;
import com.zero.library.utils.KLog;


/**
 * 作者： Wang
 * 时间： 2016/11/18
 */

public class TView extends View {


    public TView(Context context) {
        super(context);
    }

    public TView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override//测量可用控件 修改组件大小
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //根据实际情况设置默认宽高
        int width = (int) DensityUtils.dp2px(getContext(), 200);
        int height = (int) DensityUtils.dp2px(getContext(), 200);
        // 获取现在的测量模式 判断是否需要给宽高进行赋值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽高的实际大小  单位：PX
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        KLog.i("width---" + widthSize + "   height---" + heightSize);
        KLog.i("width-----" + width + "   height-----" + height);
        //判断是否需要给宽高进行重新赋值赋值
        if (widthMode != MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(width, height);
        }
        if (widthMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(width, heightMeasureSpec);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthMeasureSpec, height);
        }
    }
}
