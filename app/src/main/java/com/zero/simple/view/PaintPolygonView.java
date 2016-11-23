package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zero.library.utils.KLog;
import com.zero.simple.R;

/**
 * 多边形
 * 作者： Wang
 * 时间： 2016/11/21
 */
public class PaintPolygonView extends View {
    /**
     * 图形样式
     */
    private int paintShape = 0;
    /**
     * 图形颜色
     */
    private int color = 0;
    /**
     * Paint 宽度
     */
    private float strokeWidth = 0;
    private Paint paint;
    private RectF rectF;
    private Path path;
    private PointF movePoint;

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPaintShape() {
        return paintShape;
    }

    public void setPaintShape(int paintShape) {
        this.paintShape = paintShape;
    }

    public class Shape {
        /**
         * 矩形
         */
        public static final int RECTANGLE = 0;
        /**
         * 圆形
         */
        public static final int ROUND = 1;
        /**
         * 三角形
         */
        public static final int TRIANGLE = 2;
        /**
         * 六边形
         */
        public static final int HEXAGON = 3;
        /**
         * 五角星
         */
        public static final int PENTAGRAMS = 4;
        /**
         * 心形
         */
        public static final int HEART = 5;

        /**
         * 贝塞尔
         */
        public static final int BESSEL = 6;
    }

    public PaintPolygonView(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public PaintPolygonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public PaintPolygonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaintPolygonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        movePoint = new PointF();
        paint = new Paint();
        rectF = new RectF();
        path = new Path();
        if (attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.View_PaintPolygonView, defStyleAttr, defStyleRes);
                int count = typedArray.getIndexCount();
                for (int i = 0; i < count; i++) {
                    switch (typedArray.getIndex(i)) {
                        case R.styleable.View_PaintPolygonView_paintShape:
                            setPaintShape(typedArray.getInt(i, 0));
                            break;
                        case R.styleable.View_PaintPolygonView_paintColor:
                            setColor(typedArray.getColor(i, Color.BLACK));
                            break;
                        case R.styleable.View_PaintPolygonView_strokeWidth:
                            setStrokeWidth(typedArray.getDimension(i, 2));
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
        int height = MeasureSpec.getSize(getMeasuredHeight());
        rectF.left = getPaddingLeft();
        rectF.top = getPaddingTop();
        rectF.right = width - getPaddingRight();
        rectF.bottom = height - getPaddingBottom();
        KLog.i("RectF   left " + rectF.left + "   top " + rectF.top + "   right  " + rectF.right + "  bottom  " + rectF.bottom);


        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(getColor());
        paint.setStrokeWidth(getStrokeWidth());
        switch (getPaintShape()) {
            case Shape.RECTANGLE://矩形
                canvas.drawRect(rectF, paint);
                paint.reset();
                break;
            case Shape.ROUND://圆形
                canvas.drawRoundRect(rectF, rectF.left + (rectF.right - rectF.left) / 2, rectF.top + (rectF.bottom - rectF.top) / 2, paint);
                paint.reset();
                break;
            case Shape.TRIANGLE://三角形
                path.moveTo(rectF.left + (rectF.right - rectF.left) / 2, rectF.top);
                path.lineTo(rectF.left, rectF.bottom);
                path.lineTo(rectF.right, rectF.bottom);
                path.close();
                canvas.drawPath(path, paint);
                paint.reset();
                path.reset();
                break;
            case Shape.PENTAGRAMS://五角星
                break;
            case Shape.HEXAGON://六边形
                break;
            case Shape.HEART://心形
                break;
            case Shape.BESSEL://贝塞尔
                path.moveTo(rectF.left, rectF.bottom);
                path.quadTo(movePoint.x, movePoint.y, rectF.right, rectF.bottom);
                canvas.drawPath(path, paint);
                paint.reset();
                path.reset();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float widthX = event.getX();
        float heightY = event.getY();
        if (event.getX() < 0) {
            widthX = 0;
        } else if (event.getX() > getWidth()) {
            widthX = getWidth();
        }
        if (event.getY() < 0) {
            heightY = 0;
        } else if (event.getY() > getHeight()) {
            heightY = getHeight();
        }
        movePoint.set(widthX, heightY);
        invalidate();
        return true;
    }
}
