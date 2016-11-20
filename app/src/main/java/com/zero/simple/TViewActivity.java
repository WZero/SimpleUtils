package com.zero.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zero.simple.base.BaseActivity;

/**
 * 自定义 View
 */
public class TViewActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_tView:
//                showToast(new Date().getTime() + "");
                Intent intent = new Intent(getApplicationContext(), TwoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
