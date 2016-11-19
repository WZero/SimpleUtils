package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zero.library.utils.DensityUtils;
import com.zero.library.utils.KLog;
import com.zero.simple.R;


/**
 * 作者： Wang
 * 时间： 2016/11/18
 */

public class TView extends View {
    /**
     * 设置边宽
     */
    private float strokeWidth;

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    private Paint mPaint;
    /**
     * 边框颜色
     */
    private int frameColor = Color.RED;
    /**
     * 内容颜色
     */
    private int contentColor = Color.BLACK;
    private RectF rectF;

    public TView(Context context) {
        super(context);
        init();
    }

    public TView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        TypedArray typedArray = null;
        try {
            typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.View_TView, defStyleAttr, defStyleRes);
            setStrokeWidth(typedArray.getDimension(typedArray.getIndex(R.styleable.View_TView_strokeWidth), 1));
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }
    }

    private void init() {
        mPaint = new Paint();
        rectF = new RectF();
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
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        KLog.i("width---" + widthSize + "   height---" + heightSize);
//        KLog.i("width-----" + width + "   height-----" + height);
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

    float widthX = -1;
    float heightY = -1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (widthX < 0) {
            widthX = getWidth() / 2;
        }
        if (heightY < 0) {
            heightY = getHeight() / 2;
        }
//
//        //矩形 外
//        mPaint.setColor(contentColor);

        rectF.left = widthX - DensityUtils.dp2px(getContext(), 60) / 2;
        rectF.top = heightY - DensityUtils.dp2px(getContext(), 50) / 2;
        rectF.right = rectF.left + DensityUtils.dp2px(getContext(), 60);
        rectF.bottom = rectF.top + DensityUtils.dp2px(getContext(), 50);

//        canvas.drawRect(rectF, mPaint);
//        mPaint.reset();
//
//        //矩形 内
//        mPaint.setColor(Color.WHITE);
//        rectF.left = rectF.left + 10;
//        rectF.top = rectF.top + 10;
//        rectF.right = rectF.right - 10;
//        rectF.bottom = rectF.bottom - 10;
//        canvas.drawRect(rectF, mPaint);
//        mPaint.reset();

        //直线 A
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(getStrokeWidth());
        canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, mPaint);
        mPaint.reset();

        //直线 B
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(getStrokeWidth());
        canvas.drawLine(rectF.left, rectF.bottom, rectF.right, rectF.top, mPaint);
        mPaint.reset();
        //带边框的矩形+++++++
        mPaint.setColor(contentColor);
        mPaint.setStrokeWidth(getStrokeWidth());
        mPaint.setStyle(Paint.Style.STROKE);//设置显示样
        // 式 设置为中空
        canvas.drawRect(rectF, mPaint);
        mPaint.reset();

        //绘制圆形
        mPaint.setColor(frameColor);
        mPaint.setStyle(Paint.Style.STROKE);//设置为中空
        mPaint.setStrokeWidth(getStrokeWidth());
        mPaint.setAntiAlias(true);//设置抗锯齿
        canvas.drawCircle(widthX, heightY, DensityUtils.dp2px(getContext(), 15), mPaint);
        mPaint.reset();

        //绘画椭圆
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(getStrokeWidth());
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置为中空
        canvas.drawOval(rectF, mPaint);
        mPaint.reset();

        //绘制文字

        mPaint.setColor(frameColor);
        mPaint.setTextSize(DensityUtils.sp2px(getContext(), 15));
        mPaint.setUnderlineText(true);//是否显示下划线
        canvas.drawText("你好啊", widthX, rectF.bottom + mPaint.getTextSize(), mPaint);
        mPaint.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                KLog.i("-----MotionEvent.ACTION_DOWN");
                frameColor = (frameColor == Color.RED ? Color.BLACK : Color.RED);
                contentColor = (contentColor == Color.RED ? Color.BLACK : Color.RED);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
//                KLog.i("-----MotionEvent.ACTION_MOVE---Y  " + event.getY() + "  ---X  " + event.getX());
                widthX = event.getX();
                heightY = event.getY();
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
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
//                KLog.i("-----MotionEvent.ACTION_UP");
                frameColor = (frameColor == Color.RED ? Color.BLACK : Color.RED);
                contentColor = (contentColor == Color.RED ? Color.BLACK : Color.RED);
                invalidate();
                break;
            default:
                KLog.i("Action-----" + event.getAction());
                break;
        }
        return true;
    }
}
