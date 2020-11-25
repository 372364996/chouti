package com.chewawa.baselibrary.base;

import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 封装adapter（注意：仅供参考，根据需要选择使用demo中提供的封装adapter）
 *
 * @param <T>
 */
public abstract class BaseCheckRecycleViewAdapter<T extends BaseCheckRecycleViewAdapter.CheckItem> extends BaseRecycleViewAdapter<T> {
    private boolean enabledCheckMode;  //激活选择模式
    private boolean singleMode;  //单选模式
    private boolean singleModeIsCanCancel; //单选模式选中后是否可取消选中项
    private int currentCheckedPosition = -1;
    private int secondCheckedPosition = -1;
    /**
     * 是否是单选模式
     */
    public boolean isSingleMode() {
        return singleMode;
    }

    /**
     * 设置单选模式，默认是复选模式
     */
    public void setSingleMode(boolean singleMode) {
        this.singleMode = singleMode;
    }

    /**
     * 单选模式选中后是否可取消选中项
     * @param isCancel true为可取消，false为不可取消（必须有一项被选中）
     */
    public void setSingleModeIsCanCancel(boolean isCancel){
        this.singleModeIsCanCancel = isCancel;
    }
    /**
     * 处理复选框
     */
    public void handleCompoundButton(CompoundButton compoundButton, T t) {
        compoundButton.setChecked(t.isChecked());
        compoundButton.setVisibility(isEnabledCheckMode() ? View.VISIBLE : View.GONE);
    }

    /**
     * 激活选择模式
     */
    public void enableCheckMode() {
        if (enabledCheckMode) {
            return;
        }
        enabledCheckMode = true;
        notifyDataSetChanged();
    }

    /**
     * 取消选择模式
     */
    public void cancelCheckMode() {
        if (!enabledCheckMode) {
            return;
        }
        enabledCheckMode = false;
        for (T item : mData) {
            item.setChecked(false);
        }
        notifyDataSetChanged();
    }

    /**
     * 判定是否已激活选择模式
     */
    public boolean isEnabledCheckMode() {
        return enabledCheckMode;
    }

    /**
     * 点击了某一项
     *
     * @return true：已经激活了选择模式并且设置成功；false：尚未激活选择模式并且设置失败
     */
    public boolean clickItem(int position, boolean isSecond) {
        position -= getHeaderLayoutCount();
        if (isEnabledCheckMode()) {
            if (position < mData.size()) {
                if (singleMode) {
                    if(isSecond){
                        if (secondCheckedPosition == -1) {
                            T item = mData.get(position);
                            item.setChecked(true);
                        } else if(secondCheckedPosition == position){
                            if(singleModeIsCanCancel){
                                T item = mData.get(position);
                                item.setChecked(!item.isChecked());
                            }
                        }else {
                            if(currentCheckedPosition<mData.size()){
                                mData.get(currentCheckedPosition).setChecked(false);
                            }
                            mData.get(position).setChecked(true);
                        }
                        secondCheckedPosition = position;
                    }else {
                        secondCheckedPosition = -1;
                        if (currentCheckedPosition == -1) {
                            T item = mData.get(position);
                            item.setChecked(!item.isChecked());
                        } else if(currentCheckedPosition == position){
                            if(singleModeIsCanCancel){
                                T item = mData.get(position);
                                item.setChecked(!item.isChecked());
                            }
                        }else {
                            if(currentCheckedPosition<mData.size()){
                                mData.get(currentCheckedPosition).setChecked(false);
                            }
                            mData.get(position).setChecked(true);
                        }
                        currentCheckedPosition = position;
                    }

                } else {
                    T item = mData.get(position);
                    item.setChecked(!item.isChecked());
                }
                notifyDataSetChanged();
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * 全选
     *
     * @return true：已经激活了选择模式并且设置成功；false：尚未激活选择模式并且设置失败
     */
    public boolean checkAll(boolean isChecked) {
        if (isEnabledCheckMode()) {
            for(int i = 0; i< mData.size(); i++){
                T item = mData.get(i);
                item.setChecked(isChecked);
            }
            notifyDataSetChanged();
            if(!isChecked){
                currentCheckedPosition = -1;
                secondCheckedPosition = -1;
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * 获取选中的项
     */
    public List<T> getCheckedItems() {
        List<T> checkedItems = new ArrayList<T>();
        for (T item : mData) {
            if (item.isChecked()) {
                checkedItems.add(item);
            }
        }
        return checkedItems;
    }
    /**
     * 获取集合中选中的项
     */
    public List<T> getCheckedItems(List<T> list) {
        List<T> checkedItems = new ArrayList<T>();
        for (T item : list) {
            if (item.isChecked()) {
                checkedItems.add(item);
            }
        }
        return checkedItems;
    }
    /**
     * 删除选中的项
     */
    public List<T> deleteCheckedItems() {
        List<T> checkedItems = new ArrayList<T>();
        Iterator<T> iterator = mData.iterator();
        T item;
        while (iterator.hasNext()) {
            item = iterator.next();
            if (item.isChecked()) {
                checkedItems.add(item);
                iterator.remove();
            }
        }
        notifyDataSetChanged();
        currentCheckedPosition = -1;
        secondCheckedPosition = -1;
        return checkedItems;
    }

    public interface CheckItem extends MultiItemEntity {
        public boolean isChecked();

        public void setChecked(boolean checked);
    }
}
