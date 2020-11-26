package com.hanzhi.chouti.ui.selectclass.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chewawa.baselibrary.base.BaseCheckRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.appointment.AppointmentTimeBean;
import com.hanzhi.chouti.bean.selectclass.ClassBean;

import butterknife.BindView;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 16:58
 */
public class SelectClassAdapter extends BaseCheckRecycleViewAdapter<ClassBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.gridview_item;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<ClassBean, SelectClassAdapter> {

        public ViewHolder(SelectClassAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(ClassBean data, int position) {
            if (data == null) {
                return;
            }
        }
    }
}
