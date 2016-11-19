package com.zero.simple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.zero.library.utils.DensityUtils;


/**
 * 作者： Wang
 * 时间： 2016/11/18
 */

public class TView extends View {
    private Paint mPaint;

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
    }

    private void init() {
        mPaint = new Paint();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
//        两个矩形s实现边框
//
//        //矩形 外
//        mPaint.setColor(Color.BLACK);
        RectF rectF = new RectF();
        rectF.left = (width - DensityUtils.dp2px(getContext(), 60)) / 2;
        rectF.top = (height - DensityUtils.dp2px(getContext(), 50)) / 2;
        rectF.right = rectF.left + DensityUtils.dp2px(getContext(), 60);
        rectF.bottom = rectF.top + DensityUtils.dp2px(getContext(), 50);
        float strokeWidth = DensityUtils.dp2px(getContext(), 2);

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
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, mPaint);
        mPaint.reset();

        //直线 B
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawLine(rectF.left, rectF.bottom, rectF.right, rectF.top, mPaint);
        mPaint.reset();
        //带边框的矩形
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);//设置显示样式 设置为中空
        canvas.drawRect(rectF, mPaint);
        mPaint.reset();

        //绘制圆形
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);//设置为中空
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setAntiAlias(true);//设置抗锯齿
        canvas.drawCircle(width / 2, height / 2, DensityUtils.dp2px(getContext(), 15), mPaint);
        mPaint.reset();

        //绘画椭圆
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置为中空
        canvas.drawOval(rectF, mPaint);
        mPaint.reset();

        //绘制文字

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(DensityUtils.sp2px(getContext(), 15));
        mPaint.setUnderlineText(true);//是否显示下划线
        canvas.drawText("你好啊", width / 2, rectF.bottom + mPaint.getTextSize(), mPaint);
        mPaint.reset();
    }
}
