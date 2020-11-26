package com.hanzhi.chouti.ui.appointment.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.hanzhi.chouti.bean.teachers.AppointmentTimeBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.appointment.adapter.AppointmentTimeAdapter;

import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class AppointmentTimeChildFragment extends BaseRecycleViewFragment<AppointmentTimeBean> {
    public static final String STATUS = "status";
    public static AppointmentTimeChildFragment newInstance(int position) {
        AppointmentTimeChildFragment appointmentTimeChildFragment = new AppointmentTimeChildFragment();
        Bundle args = new Bundle();
        args.putInt(STATUS, position);
        appointmentTimeChildFragment.setArguments(args);
        return appointmentTimeChildFragment;
    }
    @Override
    protected String getUrl() {
        return Constants.GET_CLASS_TIME_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.put("size", 200);
        return params;
    }

    @Override
    protected Class<AppointmentTimeBean> getResultClass() {
        return AppointmentTimeBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter<AppointmentTimeBean> getAdapter() {
        return new AppointmentTimeAdapter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 7);
    }
}
