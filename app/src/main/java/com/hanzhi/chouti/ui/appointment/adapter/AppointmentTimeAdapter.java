package com.hanzhi.chouti.ui.appointment.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chewawa.baselibrary.base.BaseCheckRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.appointment.AppointmentTimeBean;

import butterknife.BindView;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 16:58
 */
public class AppointmentTimeAdapter extends BaseCheckRecycleViewAdapter<AppointmentTimeBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.gridview_yuyue_item;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<AppointmentTimeBean, AppointmentTimeAdapter> {
        @BindView(R.id.cb_check)
        CheckBox cbCheck;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_time_lay)
        LinearLayout llTimeLay;
        public ViewHolder(AppointmentTimeAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(AppointmentTimeBean data, int position) {
            if (data == null) {
                return;
            }
            tvTime.setText(data.getTime());
            if(data.isChecked()){
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.color_button_primary));
            }else {
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.text_color_33));
            }
            getAdapter().handleCompoundButton(cbCheck, data);
            cbCheck.setVisibility(View.GONE);
        }
    }
}
