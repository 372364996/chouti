package com.hanzhi.chouti.ui.teachers.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.teachers.AppraiseBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.teachers.adapter.AppraiseAdapter;
import com.hanzhi.chouti.utils.RequestParamsUtils;

import java.util.Map;

public class AppraiseFragment extends BaseRecycleViewFragment<AppraiseBean>  {
    ClassApplyBean classApplyBean;
    public static AppraiseFragment newInstance(ClassApplyBean classApplyBean) {
        AppraiseFragment appraiseFragment = new AppraiseFragment();
        Bundle args = new Bundle();
        args.putParcelable("classApplyBean", classApplyBean);
        appraiseFragment.setArguments(args);
        return appraiseFragment;
    }
    @Override
    protected String getUrl() {
        return Constants.GET_APPRAISE_LIST_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.putAll(RequestParamsUtils.getClassApplyParams(classApplyBean));
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        if(getArguments() != null){
            classApplyBean = getArguments().getParcelable("classApplyBean");
        }
    }

    @Override
    protected Class<AppraiseBean> getResultClass() {
        return AppraiseBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter getAdapter() {
        return new AppraiseAdapter();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        Log.e("整体item----->", position + "");

    }
}

