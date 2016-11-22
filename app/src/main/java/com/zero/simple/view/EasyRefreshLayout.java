package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.zero.library.utils.KLog;
import com.zero.simple.R;

/**
 * 作者： Wang
 * 时间： 2016/11/22
 */

public class EasyRefreshLayout extends RelativeLayout {

    private Scroller mScroller;
    private GestureDetector mGestureDetector;
    private View mRecyclerView;
    private View refreshView;
    private View pullView;
    private int recyclerViewId = -1;
    private int refreshViewId = -1;
    private int pullViewId = -1;

    public int getPullViewId() {
        return pullViewId;
    }

    public void setPullViewId(int pullViewId) {
        this.pullViewId = pullViewId;
    }

    public int getRefreshViewId() {
        return refreshViewId;
    }

    public void setRefreshViewId(int refreshViewId) {
        this.refreshViewId = refreshViewId;
    }

    public int getRecyclerViewId() {
        return recyclerViewId;
    }

    public void setRecyclerViewId(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public View getPullView() {
        return pullView;
    }

    public void setPullView(View pullView) {
        if (pullView != null) {
            if (pullView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) pullView.getLayoutParams();
                p.setMargins(0, 0, 0, -pullView.getHeight());
                pullView.requestLayout();
            }
        }
        this.pullView = pullView;
        setPullViewId(-1);
    }

    public View getRefreshView() {
        return refreshView;
    }

    public void setRefreshView(View refreshView) {
        KLog.i("-----" + refreshView);
        if (refreshView != null) {
            if (refreshView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) refreshView.getLayoutParams();
                p.setMargins(0, -refreshView.getHeight(), 0, 0);
                refreshView.requestLayout();
            }
        }
        this.refreshView = refreshView;
        setRefreshViewId(-1);
    }

    public View getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(View recyclerView) {
        this.mRecyclerView = recyclerView;
        setRecyclerViewId(-1);
    }

    public EasyRefreshLayout(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public EasyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public EasyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mScroller = new Scroller(getContext());
        mGestureDetector = new GestureDetector(getContext(), onGestureListener);
        if (attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.View_EasyRefreshLayout, defStyleAttr, defStyleRes);
                int indexCount = typedArray.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    switch (typedArray.getIndex(i)) {
                        case R.styleable.View_EasyRefreshLayout_recyclerViewId:
                            KLog.i("-----" + findViewById(R.id.recycler_refresh));
                            setRecyclerViewId(typedArray.getResourceId(i, 0));
                            break;
                        case R.styleable.View_EasyRefreshLayout_refreshViewId:
                            setRefreshViewId(typedArray.getResourceId(i, 0));
                            break;
                        case R.styleable.View_EasyRefreshLayout_pullViewId:
                            setPullViewId(typedArray.getResourceId(i, 0));
                            break;
                    }
                }
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getRecyclerViewId() != -1 || getRefreshViewId() != -1 || getPullViewId() != -1) {
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (child.getId() == getRecyclerViewId()) {
                    setRecyclerView(child);
                } else if (child.getId() == getRefreshViewId()) {
                    setRefreshView(child);
                } else if (child.getId() == getPullViewId()) {
                    setPullView(child);
                }
            }
        }
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
            int disY = (int) ((distanceY - 0.5) / 3);
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
        if (isRefresh && getRefreshView() != null && Math.abs(mScroller.getFinalY() + dy) > getRefreshView().getHeight()) {
            dy = mScroller.getFinalY() + getRefreshView().getHeight();
        } else if (isRefresh && getRefreshView() == null) {
            dy = 0;
        }

        if (isPull && getPullView() != null && Math.abs(mScroller.getFinalY() + dy) > getPullView().getHeight()) {
            dy = mScroller.getFinalY() - getPullView().getHeight();
        } else if (isRefresh && getPullView() == null) {
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
