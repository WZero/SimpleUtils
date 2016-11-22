package com.zero.simple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * 支持加头 加尾 Item 点击 单一样式  Adapter
 * 作者： Wang
 * 时间： 2016/11/16
 */
public class SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<SimpleRecyclerAdapter.BaseRecyclerViewHolder> {
    private List<T> stringList;
    private int id;
    private AdapterViewHolder adapterViewHolder;
    private OnClickItemListener onClickItemListener;
    private LinearLayout headLinearLayoutView;
    private LinearLayout footLinearLayoutView;
    private final int HEAD_ITEM = 0;
    private final int CONTENT_ITEM = 1;
    private final int FOOT_ITEM = 2;
    private Context context;

    public SimpleRecyclerAdapter(Context context, List<T> stringList, int id) {
        this.stringList = stringList;
        this.id = id;
        this.context = context;
    }

    /**
     * @return LinearLayout
     */
    public LinearLayout getFootView() {
        if (footLinearLayoutView == null) {
            footLinearLayoutView = new LinearLayout(context);
            footLinearLayoutView.setOrientation(LinearLayout.VERTICAL);
            footLinearLayoutView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            footLinearLayoutView.setMinimumHeight(1);
            notifyDataSetChanged();
        }
        return footLinearLayoutView;
    }

    /**
     * @return LinearLayout
     */
    public LinearLayout getHeadView() {
        if (headLinearLayoutView == null) {
            headLinearLayoutView = new LinearLayout(context);
            headLinearLayoutView.setOrientation(LinearLayout.VERTICAL);
            headLinearLayoutView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            headLinearLayoutView.setMinimumHeight(1);
            notifyDataSetChanged();
        }
        return headLinearLayoutView;
    }

    /**
     * 添加Item点击事件
     *
     * @param onClickItemListener OnClickItemListener
     */
    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    /**
     * 添加 ViewHolder 回调
     *
     * @param adapterViewHolder AdapterViewHolder
     */
    public void setAdapterViewHolder(AdapterViewHolder adapterViewHolder) {
        this.adapterViewHolder = adapterViewHolder;
    }

    @Override
    public SimpleRecyclerAdapter.BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_ITEM) {
            return new HeadFootViewHolder(getHeadView());
        } else if (viewType == FOOT_ITEM) {
            return new HeadFootViewHolder(getFootView());
        } else if (viewType == CONTENT_ITEM) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(id, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(SimpleRecyclerAdapter.BaseRecyclerViewHolder holder, int position) {
        holder.showItem();
    }

    @Override
    public int getItemViewType(int position) {
        if (headLinearLayoutView != null && position == 0) {
            return HEAD_ITEM;
        } else if (footLinearLayoutView != null && position == getItemCount() - (headLinearLayoutView == null ? 0 : 1)) {
            return FOOT_ITEM;
        } else {
            return CONTENT_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (headLinearLayoutView != null) {
            count++;
        }
        if (footLinearLayoutView != null) {
            count++;
        }
        return stringList == null ? count : stringList.size() + count;
    }


    /**
     * 头部跟底部 ViewHolder
     */
    private class HeadFootViewHolder extends BaseRecyclerViewHolder {

        private HeadFootViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void showItem() {

        }
    }

    /**
     * 内容区 ViewHolder
     */
    private class ContentViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {

        private ContentViewHolder(View itemView) {
            super(itemView);
            if (onClickItemListener != null)
                itemView.setOnClickListener(this);
        }

        @Override
        public void showItem() {
            if (adapterViewHolder != null)
                adapterViewHolder.onViewHolder(itemView, getAdapterPosition() - (headLinearLayoutView == null ? 0 : 1));
        }

        @Override
        public void onClick(View view) {
            onClickItemListener.onItemClick(view, getAdapterPosition() - (headLinearLayoutView == null ? 0 : 1));
        }
    }

    /**
     * ViewHolder 回调
     */
    public interface AdapterViewHolder {
        void onViewHolder(View itemView, int position);
    }

    /**
     * Item 点击事件回调
     */
    public interface OnClickItemListener {
        void onItemClick(View view, int position);
    }

    /**
     * BaseViewHolder
     */
    abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

        private BaseRecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void showItem();
    }
}
