package com.zero.simple;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zero.library.utils.KLog;
import com.zero.simple.adapter.SimpleRecyclerAdapter;
import com.zero.simple.base.BaseActivity;
import com.zero.simple.view.EasyRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RefreshActivity extends BaseActivity implements SimpleRecyclerAdapter.AdapterViewHolder, SimpleRecyclerAdapter.OnClickItemListener, EasyRefreshLayout.OnEasyRefresh {

    private RecyclerView recyclerView;
    List<String> stringList;
    private SimpleRecyclerAdapter<String> adapter;
    private EasyRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        stringList = new ArrayList<>();
        addData(stringList, 20);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        refreshLayout = (EasyRefreshLayout) findViewById(R.id.activity_refresh);
        refreshLayout.setRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new SimpleRecyclerAdapter<>(getApplicationContext(), stringList, R.layout.racycler_item);
        recyclerView.setAdapter(adapter);
        adapter.setAdapterViewHolder(this);
        adapter.setOnClickItemListener(this);
        refreshLayout.setOnEasyRefresh(this);
    }

    private void addData(List<String> list, int count) {
        for (int i = 0; i < count; i++) {
            list.add(new Date().getTime() + "---" + i);
        }
    }


    @Override
    public void onViewHolder(View itemView, int position) {
        TextView textView = (TextView) itemView.findViewById(R.id.recycler_item_text);
        textView.setText(stringList.get(position));
    }

    @Override
    public void onItemClick(View view, int position) {
        KLog.i("--------" + position);
    }

    @Override
    public void onRefresh() {
        KLog.i("下拉刷新......");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stringList.clear();
                addData(stringList, 20);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshOver();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onPull() {
        KLog.i("上拉加载......");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addData(stringList, 20);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshOver();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
