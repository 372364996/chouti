package com.hanzhi.chouti.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.mine.MyClassBean;
import com.hanzhi.chouti.bean.mine.MyClassTabBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.appointment.AppointmentTimeActivity;
import com.hanzhi.chouti.ui.mine.MyClassDetailActivity;
import com.hanzhi.chouti.ui.mine.adapter.MyClassAdapter;
import com.hanzhi.chouti.ui.mine.contract.MyClassContract;
import com.hanzhi.chouti.ui.mine.presenter.MyClassPresenter;

import java.util.List;
import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class MyClassChildFragment extends BaseRecycleViewFragment<MyClassBean> implements MyClassContract.View {
    public static final String ID = "id";
    int id;
    MyClassPresenter myClassPresenter;
    public static MyClassChildFragment newInstance(int id) {
        MyClassChildFragment myClassChildFragment = new MyClassChildFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        myClassChildFragment.setArguments(args);
        return myClassChildFragment;
    }
    @Override
    protected String getUrl() {
        return Constants.GET_MY_CLASS_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.put("size", 20);
        params.put("status", id);
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        if(getArguments() != null){
            id = getArguments().getInt(ID, 0);
        }
    }

    @Override
    public BasePresenterImpl initPresenter() {
        myClassPresenter = new MyClassPresenter(this);
        return super.initPresenter();
    }

    @Override
    protected Class<MyClassBean> getResultClass() {
        return MyClassBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter<MyClassBean> getAdapter() {
        return new MyClassAdapter();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemChildClick(adapter, view, position);
        MyClassBean myClassBean = (MyClassBean) adapter.getItem(position);
        if(myClassBean == null){
            return;
        }
        switch (view.getId()){
            case R.id.btn_reapply:{
                AppointmentTimeActivity.start(getActivity(), null);
                break;
            }
            case R.id.btn_cancel:{
                myClassPresenter.cancelClass(myClassBean.getId());
                break;
            }
            case R.id.btn_review:{
                MyClassDetailActivity.start(getActivity(), myClassBean.getClassId());
                break;
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        MyClassBean myClassBean = (MyClassBean) adapter.getItem(position);
        if(myClassBean == null){
            return;
        }
        MyClassDetailActivity.start(getActivity(), myClassBean.getClassId());
    }

    @Override
    public void setTabList(List<MyClassTabBean> list, List<String> titleList) {

    }

    @Override
    public void refreshList() {
        onRefresh();
    }
}