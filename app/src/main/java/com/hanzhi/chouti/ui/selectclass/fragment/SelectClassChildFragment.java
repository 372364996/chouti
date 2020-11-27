package com.hanzhi.chouti.ui.selectclass.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.appointment.AppointmentTimeBean;
import com.hanzhi.chouti.bean.selectclass.ClassBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.appointment.adapter.AppointmentTimeAdapter;
import com.hanzhi.chouti.ui.selectclass.adapter.SelectClassAdapter;
import com.hanzhi.chouti.utils.RequestParamsUtils;

import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class SelectClassChildFragment extends BaseRecycleViewFragment<ClassBean> {
    public static final String ID = "id";
    int id;
    ClassApplyBean classApplyBean;
    public static SelectClassChildFragment newInstance(int id, ClassApplyBean classApplyBean) {
        SelectClassChildFragment selectClassChildFragment = new SelectClassChildFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        args.putParcelable("classApplyBean", classApplyBean);
        selectClassChildFragment.setArguments(args);
        return selectClassChildFragment;
    }
    @Override
    protected String getUrl() {
        return Constants.GET_CLASS_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.put("size", 30);
        params.put("type", id);
        params.putAll(RequestParamsUtils.getClassApplyParams(classApplyBean));
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        if(getArguments() != null){
            id = getArguments().getInt(ID, 0);
            classApplyBean = getArguments().getParcelable("classApplyBean");
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
    }
}
