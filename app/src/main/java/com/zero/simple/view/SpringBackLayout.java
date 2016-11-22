package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView mRecyclerView;

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.i(canChildScrollTop() + "--canChildScrollTop---" + canChildScrollBottom());
        requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }

    float startY;
    /**
     * 下拉刷新
     */
    boolean isRefresh;
    /**
     * 上拉加载
     */
    boolean isPull;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getRecyclerView() == null) {
            return super.onInterceptTouchEvent(ev);
        }
        if (canChildScrollTop() && canChildScrollBottom()) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                isRefresh = false;
                isPull = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!canChildScrollTop() && startY != 0 && startY - ev.getY() < 0) {
                    isRefresh = true;
                    return true;
                }
                if (!canChildScrollBottom() && startY != 0 && startY - ev.getY() > 0) {
                    isPull = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startY = 0;
                break;
        }
        return super.onInterceptTouchEvent(ev);
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
            int disY = (int) ((distanceY - 0.5) / 2);
//            KLog.i("distanceY--------- " + mScroller.getFinalY() + "   " + distanceY + "    " + disY + "   " + canChildScrollTop());
            if (isRefresh && !canChildScrollTop() && (mScroller.getFinalY() + distanceY) > 0) {
                disY = 0 - mScroller.getFinalY();
            }
            if (isPull && !canChildScrollBottom() && (mScroller.getFinalY() + distanceY) < 0) {
                disY = 0 - mScroller.getFinalY();
            }
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
        if (Math.abs(mScroller.getFinalY() + dy) > getHeight() / 5) {
            dy = 0;
        }
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();
    }

    /**
     * @return 是否可向上滑动
     */
    public boolean canChildScrollTop() {
        return ViewCompat.canScrollVertically(getRecyclerView(), -1);
    }

    /**
     * @return 是否可向下滑动
     */
    public boolean canChildScrollBottom() {
        return ViewCompat.canScrollVertically(getRecyclerView(), 1);
    }
}
