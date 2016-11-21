package com.zero.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zero.simple.adapter.SimpleRecyclerAdapter;
import com.zero.simple.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements SimpleRecyclerAdapter.AdapterViewHolder, SimpleRecyclerAdapter.OnClickItemListener {

    private RecyclerView recyclerView;
    private SimpleRecyclerAdapter<String> adapter;
    List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stringList = new ArrayList<>();
        addData();
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new SimpleRecyclerAdapter<>(getApplicationContext(), stringList, R.layout.main_item);
        adapter.setAdapterViewHolder(this);
        adapter.setOnClickItemListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 添加数据
     */
    void addData() {
        stringList.add("自定义 View - TView");
        stringList.add("Bitmap - 圆、圆弧 图片");
        stringList.add("Paint 多边形");
    }

    @Override
    public void onViewHolder(View itemView, int position) {
        TextView textView = (TextView) itemView.findViewById(R.id.main_item_text);
        textView.setText(stringList.get(position));
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(getApplicationContext(), TViewActivity.class);
                break;
            case 1:
                intent = new Intent(getApplicationContext(), BitmapActivity.class);
                break;
            case 2:
                intent = new Intent(getApplicationContext(), PaintsActivity.class);
                break;
        }
        if (intent != null) {
            intent.putExtra("title", stringList.get(position));
            startActivity(intent);
        }
    }
}
