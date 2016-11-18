package com.zero.simple.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 作者： Wang
 * 时间： 2016/11/18
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

    protected void showToast(CharSequence charSequence, int duration) {
        if (cToast == null) {
            cToast = Toast.makeText(getApplicationContext(), charSequence, duration);
            cToast.show();
        } else {
            cToast.setText(charSequence);
            cToast.setDuration(duration);
            cToast.show();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (isIntent) {
            isIntent = false;
            super.startActivityForResult(intent, requestCode, options);
        }
    }

}
