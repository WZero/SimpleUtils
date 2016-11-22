package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import com.zero.library.utils.KLog;

/**
 * 作者： Wang
 * 时间： 2016/11/22
 */

public class EasyScrollView extends View {
    public EasyScrollView(Context context) {
        super(context);
        init();
    }

    public EasyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EasyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    Paint mPaint;
    Scroller mScroller;

    void init() {
        mPaint = new Paint();
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(0, 0);
        } else if (widthMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(0, heightSize);
        } else if (heightMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, 0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = MeasureSpec.getSize(getMeasuredWidth());
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        int lift = getPaddingLeft();
        int right = getPaddingRight();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        canvas.drawLine(lift, top, width - right, top, mPaint);
        mPaint.reset();
    }

    /**
     * 动画滚动到指定位置
     */
    public void zoomIn() {
        //还原当前正在进行的任何动画
        mScroller.forceFinished(true);
        mScroller.startScroll(0, 0, -200, -500,2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            scrollTo(currX, currY);
            invalidate();
        }
    }
}
