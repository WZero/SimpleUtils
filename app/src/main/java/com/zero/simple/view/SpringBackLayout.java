package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.zero.library.utils.KLog;

/**
 * 作者： Wang
 * 时间： 2016/11/22
 */

public class SpringBackLayout extends RelativeLayout {

    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    public SpringBackLayout(Context context) {
        super(context);
        init();
    }

    public SpringBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpringBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mGestureDetector = new GestureDetector(getContext(), onGestureListener);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                reset(0, 0);
                break;
            default:
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }


    GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int disY = (int) ((distanceY - 0.5) / 4);
            beginScroll(0, disY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };

    private void reset(int x, int y) {
        int dx = x - mScroller.getFinalX();
        int dy = y - mScroller.getFinalY();
        beginScroll(dx, dy);
    }

    /**
     * 刷新 Scroll
     *
     * @param dx int
     * @param dy int
     */
    private void beginScroll(int dx, int dy) {
        KLog.i("-----  " + mScroller.getFinalY());
        if (Math.abs(mScroller.getFinalY()+dy) > getHeight() / 5) {
            dy = 0;
        }
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();
    }
}
