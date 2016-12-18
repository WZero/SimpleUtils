package com.zero.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.simple.adapter.SimpleRecyclerAdapter;
import com.zero.simple.base.BaseActivity;
import com.zero.simple.recyler.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
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
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext());
        itemDecoration.setDivider(R.color.colorAccent);
        itemDecoration.setDividerHeight(R.dimen.activity_horizontal_margin);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new SimpleRecyclerAdapter<>(getApplicationContext(), stringList, R.layout.main_item);
        adapter.setAdapterViewHolder(this);
        adapter.setOnClickItemListener(this);
        recyclerView.setAdapter(adapter);
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    /**
     * 添加数据
     */
    void addData() {
        stringList.add("自定义 View - TView");
        stringList.add("Bitmap - 圆、圆弧 图片");
        stringList.add("Paint 多边形");
        stringList.add("RecyclerView Pull");
        stringList.add("Scroll 滚动");
        stringList.add("Scroll 实现回弹");
        stringList.add("自定义 RefreshLayout");
        stringList.add("测试子线程 Toast ");
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
            case 3:
                intent = new Intent(getApplicationContext(), RecyclerActivity.class);
                break;
            case 4:
                intent = new Intent(getApplicationContext(), EasyScrollActivity.class);
                break;
            case 5:
                intent = new Intent(getApplicationContext(), SpringBackActivity.class);
                break;
            case 6:
                intent = new Intent(getApplicationContext(), RefreshActivity.class);
                break;
            case 7:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(new Date().getTime() + "");
                    }
                }).start();
                break;
        }
        if (intent != null) {
            intent.putExtra("title", stringList.get(position));
            startActivity(intent);
        }
    }
}
