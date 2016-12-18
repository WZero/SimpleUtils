/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zero.simple.recyler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**o
 * RecyclerView 添加  DividerItem </br>
 * DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext());   <br/>
 * itemDecoration.setDivider(R.color.colorAccent);   <br/>
 * itemDecoration.setDividerHeight(R.dimen.activity_horizontal_margin);   <br/>
 * recyclerView.addItemDecoration(itemDecoration);   <br/>
 */
public final class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;
    private float mDividerHeight = -1;

    /**
     * @param context Context
     */
    public DividerItemDecoration(Context context) {
        this.mContext = context;
    }

    /**
     * 配置分割线样式
     *
     * @param resId DrawableRes
     */
    public void setDivider(@DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setDivider(mContext.getResources().getDrawable(resId, mContext.getTheme()));
        } else {
            setDivider(mContext.getResources().getDrawable(resId));
        }
    }

    /**
     * 配置分割线的高度(横向滚动为宽度)( 默认是 Divider 的高度 )，用 color 可以配置高度
     *
     * @param resId DimenRes
     */
    public void setDividerHeight(@DimenRes int resId) {
        mDividerHeight = mContext.getResources().getDimension(resId);
    }

    /**
     * 设置分割线样式
     *
     * @param divider Drawable
     */
    public void setDivider(Drawable divider) {
        this.mDivider = divider;
    }


    private Drawable getDivider() {
        if (mDivider == null) {
            final TypedArray a = mContext.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }
        return mDivider;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            getDivider();
            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        } else {
            super.onDraw(c, parent, state);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin +
                    Math.round(ViewCompat.getTranslationX(child));
            final int right = left + getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            getDivider();
            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, getIntrinsicHeight());
            } else {
                outRect.set(0, 0, getIntrinsicWidth(), 0);
            }
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    /**
     * 获取分割线的高度
     *
     * @return int
     */
    private int getIntrinsicHeight() {
        return mDividerHeight != -1 ? (int) mDividerHeight : mDivider.getIntrinsicHeight();
    }

    /**
     * 获取分割线的高度
     *
     * @return int
     */
    private int getIntrinsicWidth() {
        return mDividerHeight != -1 ? (int) mDividerHeight : mDivider.getIntrinsicWidth();
    }
}
