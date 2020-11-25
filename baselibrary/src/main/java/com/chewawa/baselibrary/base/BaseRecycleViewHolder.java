package com.chewawa.baselibrary.base;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chewawa.baselibrary.utils.glide.GlideUtils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * ViewHolder基类
 */
public abstract class BaseRecycleViewHolder<T, K extends BaseRecycleViewAdapter> extends BaseViewHolder {
    protected Reference<K> mViewRef;//view接口类型的弱引用
    protected Context context;
    protected GlideUtils glideUtils;
    public BaseRecycleViewHolder(K adapter, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mViewRef = new WeakReference<>(adapter);
        context = itemView.getContext();
        glideUtils = new GlideUtils(context);
    }
    public K getAdapter() {
        if(mViewRef != null){
            return  mViewRef.get();
        }else {
            return null;
        }

    }
    /**
     * 装载数据
     * <功能详细描述>
     * @param data
     * @see [类、类#方法、类#成员]
     */
    public abstract void loadData(T data,int position);
}
