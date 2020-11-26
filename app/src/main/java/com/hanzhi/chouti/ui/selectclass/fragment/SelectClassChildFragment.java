package com.hanzhi.chouti.ui.selectclass.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.hanzhi.chouti.bean.appointment.AppointmentTimeBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.appointment.adapter.AppointmentTimeAdapter;

import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class SelectClassChildFragment extends BaseRecycleViewFragment<ClassBean> {
    public static final String ID = "id";
    String date;
    public static SelectClassChildFragment newInstance(int id) {
        SelectClassChildFragment selectClassChildFragment = new SelectClassChildFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
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
        params.put("id", id);
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        if(getArguments() != null){
            id = getArguments().getString(ID);
        }
    }

    @Override
    protected Class<ClassBean> getResultClass() {
        return ClassBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter<ClassBean> getAdapter() {
        return new SelectClassAdapter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 3);
    }
}
