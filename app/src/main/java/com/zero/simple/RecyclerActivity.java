package com.zero.simple;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zero.library.utils.KLog;
import com.zero.simple.adapter.SimpleRecyclerAdapter;
import com.zero.simple.base.BaseActivity;
import com.zero.simple.recyler.EasyRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerActivity extends BaseActivity implements SimpleRecyclerAdapter.AdapterViewHolder, SwipeRefreshLayout.OnRefreshListener, SimpleRecyclerAdapter.OnClickItemListener {

    private EasyRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    List<String> stringList;
    private SimpleRecyclerAdapter<String> adapter;
//    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        stringList = new ArrayList<>();
        addData(stringList, 20);
        refreshLayout = (EasyRefreshLayout) findViewById(R.id.activity_recycler);
        if (refreshLayout == null) return;
        //设置刷新时动画的颜色，可以设置4个
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        adapter = new RecyclerAdapter(stringList);
        adapter = new SimpleRecyclerAdapter<>(getApplicationContext(), stringList, R.layout.racycler_item);
        recyclerView.setAdapter(adapter);
        adapter.setAdapterViewHolder(this);
        adapter.setOnClickItemListener(this);
    }

    private void addData(List<String> list, int count) {
        for (int i = 0; i < count; i++) {
            list.add(new Date().getTime() + "---" + i);
        }
    }

    @Override
    public void onRefresh() {
//        KLog.i("-----------onRefresh");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        addData(stringList, 20);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onViewHolder(View itemView, int position) {
        TextView textView = (TextView) itemView.findViewById(R.id.recycler_item_text);
        textView.setText(stringList.get(position));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.i("---------Activity ----");
        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(View view, int position) {
        KLog.i("--------" + position);
    }
}
