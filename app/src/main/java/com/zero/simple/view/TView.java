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

import com.easy.utils.KLog;
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
    /**
     * 设置矩形的宽高
     */
    private float rectWidth;
    private float rectHeight;
    /**
     * 圆的半径
     */
    private float circleRadius;
    /**
     * 文字大小
     */
    private float textSize;
    /**
     * 文本颜色
     */
    private int textColor;
    /**
     * 文本的内容
     */
    private String text;

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public float getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(float rectWidth) {
        this.rectWidth = rectWidth;
    }

    public float getRectHeight() {
        return rectHeight;
    }

    public void setRectHeight(float rectHeight) {
        this.rectHeight = rectHeight;
    }

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
        init(null, 0, 0);
    }

    public TView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public TView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint();
        rectF = new RectF();
        if (attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.View_TView, defStyleAttr, defStyleRes);
                int indexCount = typedArray.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    switch (typedArray.getIndex(i)) {
                        case R.styleable.View_TView_strokeWidth://获取Paint的宽度
                            setStrokeWidth(typedArray.getDimension(i, 1));
                            break;
                        case R.styleable.View_TView_rectWidth://获取矩形的宽
                            setRectWidth(typedArray.getDimension(i, 1));
                            break;
                        case R.styleable.View_TView_rectHeight://获取矩形的高
                            setRectHeight(typedArray.getDimension(i, 1));
                            break;
                        case R.styleable.View_TView_circleRadius://获取圆形的半径
                            setCircleRadius(typedArray.getDimension(i, 1));
                            break;
                        case R.styleable.View_TView_textSize://获取文版字体的大小
                            setTextSize(typedArray.getDimensionPixelSize(i, 1));
                            break;
                        case R.styleable.View_TView_text://获取文版内容
                            setText(typedArray.getString(i));
                            break;
                        case R.styleable.View_TView_textColor://获取文版内容颜色
                            setTextColor(typedArray.getColor(i, Color.BLACK));
                            break;
                    }
                }
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }
        }
    }

    @Override//测量可用控件 修改组件大小
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //根据实际情况设置默认宽高
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
            setMeasuredDimension((int) getRectWidth() + 1, (int) getRectHeight() + 1);
        }
        if (widthMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension((int) getRectWidth() + 1, heightMeasureSpec);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthMeasureSpec, (int) getRectHeight() + 1);
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

        rectF.left = widthX - getRectWidth() / 2;
        rectF.top = heightY - getRectHeight() / 2;
        rectF.right = rectF.left + getRectWidth();
        rectF.bottom = rectF.top + getRectHeight();

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
        canvas.drawCircle(widthX, heightY, getCircleRadius(), mPaint);
        mPaint.reset();

        //绘画椭圆
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(getStrokeWidth());
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置为中空
        canvas.drawOval(rectF, mPaint);
        mPaint.reset();
//        canvas.translate(-50,0); 移动坐标系

        //绘制文字
        canvas.rotate(15);//旋转画布
        mPaint.setColor(getTextColor());
        mPaint.setTextSize(getTextSize());
        mPaint.setUnderlineText(true);//是否显示下划线
        canvas.drawText(getText(), widthX, rectF.bottom + mPaint.getTextSize(), mPaint);
        mPaint.reset();

        canvas.rotate(-15);//旋转画布
        mPaint.setColor(getTextColor());
        mPaint.setTextSize(getTextSize());
        mPaint.setUnderlineText(true);//是否显示下划线
        canvas.drawText(getText(), widthX, rectF.bottom + mPaint.getTextSize(), mPaint);
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
