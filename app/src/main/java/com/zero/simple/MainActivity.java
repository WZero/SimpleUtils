package com.zero.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zero.simple.base.BaseActivity;

import java.util.Date;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_tView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tView:
//                showToast(new Date().getTime() + "");
                Intent intent = new Intent(getApplicationContext(), TwoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
