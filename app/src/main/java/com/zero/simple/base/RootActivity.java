package com.zero.simple.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * <h1>作者： Wang</h1>
 * <h1>时间： 2016/11/18</h1>
 * <p>1、处理 startActivity 重复多次跳转</p>
 * <p>2、优化 Toast 支持子线程弹出</p>
 */

public class RootActivity extends AppCompatActivity {
    private Toast cToast;
    /**
     * 判断是否可以进行跳转（处理重复跳转异常）
     */
    private boolean isIntent;

    @Override
    protected void onStart() {
        isIntent = true;
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * show Toast
     *
     * @param charSequence CharSequence
     */
    protected void showToast(CharSequence charSequence) {
        showToast(charSequence, Toast.LENGTH_SHORT);
    }

    /**
     * show Toast
     *
     * @param resId int
     */
    protected void showToast(int resId) {
        showToast(getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 支持子线程 Show
     *
     * @param charSequence CharSequence
     * @param duration     duration
     */
    protected void showToast(final CharSequence charSequence, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (cToast == null) {
                    cToast = Toast.makeText(getApplicationContext(), charSequence, duration);
                    cToast.show();
                } else {
                    cToast.setText(charSequence);
                    cToast.setDuration(duration);
                    cToast.show();
                }
            }
        });
    }

    /**
     * Activity 跳转
     *
     * @param cls Class<?>
     */
    public void startActivity(Class<?> cls) {
        super.startActivity(new Intent(getApplicationContext(), cls));
    }

    /**
     * Activity 跳转
     *
     * @param cls     Class<?>
     * @param options Bundle
     */
    public void startActivity(Class<?> cls, @Nullable Bundle options) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.putExtras(options);
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (isIntent) {
            isIntent = false;
            super.startActivityForResult(intent, requestCode, options);
        }
    }

}
