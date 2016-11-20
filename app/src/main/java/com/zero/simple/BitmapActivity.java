package com.zero.simple;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.zero.library.utils.DensityUtils;
import com.zero.library.utils.KLog;
import com.zero.simple.base.BaseActivity;

/**
 * Bitmap
 */
public class BitmapActivity extends BaseActivity {

    /**
     * 圆形图片
     */
    private ImageView imageRound;
    /**
     * 弧角图片
     */
    private ImageView imageRoundCorner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        imageRoundCorner = (ImageView) findViewById(R.id.bitmap_arcAngle);
        imageRound = (ImageView) findViewById(R.id.bitmap_round);
        //获取到图片Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bitmap_image);
        bitmap = getRoundCornerBitmap(bitmap, DensityUtils.dp2px(getApplicationContext(), 20));
        imageRoundCorner.setImageBitmap(bitmap);
        bitmap = getRoundBitmap(bitmap);
        imageRound.setImageBitmap(bitmap);
    }


    private Bitmap getRoundBitmap(@NonNull Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        KLog.i("Bitmap  Width: " + width + "   Height:  " + height);
        //创建个空 Bitmap
        Bitmap roundCornerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(roundCornerBitmap);
        //创建 Paint
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);//设置抗锯齿
        //创建 圆形
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        //合并图层
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);

        Rect rect = new Rect(0, 0, width, height);
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return roundCornerBitmap;
    }

    /**
     * 圆角图片
     *
     * @param bitmap 需要裁剪的Bitmap
     * @param pixels 圆角弧度
     * @return Bitmap
     */
    private Bitmap getRoundCornerBitmap(@NonNull Bitmap bitmap, float pixels) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        KLog.i("Bitmap  Width: " + width + "   Height:  " + height);
        //创建个空 Bitmap
        Bitmap roundCornerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(roundCornerBitmap);
        //创建 Paint
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);//设置抗锯齿
        //创建 圆角矩形
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);
        //合并图层
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return roundCornerBitmap;
    }
}
