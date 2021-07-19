package com.hanzhi.onlineclassroom.ui.appointment.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chewawa.baselibrary.base.BaseCheckRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.appointment.AppointmentTimeBean;

import butterknife.BindView;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 16:58
 */
public class SetAppointmentTimeAdapter extends BaseCheckRecycleViewAdapter<AppointmentTimeBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.gridview_yuyue_item;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<AppointmentTimeBean, SetAppointmentTimeAdapter> {
        @BindView(R.id.cb_check)
        CheckBox cbCheck;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.fl_time_lay)
        FrameLayout flTimeLay;

        public ViewHolder(SetAppointmentTimeAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(AppointmentTimeBean data, int position) {
            if (data == null) {
                return;
            }
            tvTime.setText(data.getTime());
            if (data.getIsCanUse()) {
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.text_color_33));
                tvTime.setBackgroundResource(R.drawable.rectangle_round_corner6_light_primary);

            } else {
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.text_color_99));
                tvTime.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
            }
            if (data.isChecked()) {
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.color_red));
                tvTime.setBackgroundResource(R.drawable.rectangle_round_corner6_light_primary);
            } else {
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.text_color_33));
                tvTime.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
            }
            getAdapter().handleCompoundButton(cbCheck, data);
            cbCheck.setVisibility(View.GONE);
        }
    }
}
