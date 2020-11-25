package com.chewawa.baselibrary.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装adapter
 *
 * @param <T>
 */
public abstract class BaseRecycleViewAdapter<T> extends BaseQuickAdapter<T, BaseRecycleViewHolder> {
    public BaseRecycleViewAdapter() {
        super(null);
    }

    public BaseRecycleViewAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseRecycleViewAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseRecycleViewAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected BaseRecycleViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        View itemView = (getDynamicView() == null ? getItemView(getConvertViewId(viewType), parent) : getDynamicView());
        BaseRecycleViewHolder holder = getNewHolder(itemView, viewType);
        itemView.setTag(holder);
        return holder;
    }
    public View getDynamicView() {
        return null;
    }

    public abstract int getConvertViewId(int viewType);

    public abstract BaseRecycleViewHolder getNewHolder(View view, int viewType);


    @Override
    protected void convert(BaseRecycleViewHolder helper, T item) {
        helper.loadData(item, helper.getLayoutPosition());
    }

    /**
     * 清空Adapter，使用List的clear会导致Activity中list也被清空
     */
    public void clear(){
        if(mData != null && mData.size()>0){
            mData.clear();
            notifyDataSetChanged();
        }
    }
}
