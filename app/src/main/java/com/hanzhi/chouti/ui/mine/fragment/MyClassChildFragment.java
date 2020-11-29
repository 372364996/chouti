package com.hanzhi.chouti.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.mine.MyClassBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.mine.MyClassDetailActivity;
import com.hanzhi.chouti.ui.mine.adapter.MyClassAdapter;

import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class MyClassChildFragment extends BaseRecycleViewFragment<MyClassBean> {
    public static final String ID = "id";
    int id;
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
        params.put("size", 30);
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
        switch (view.getId()){
            case R.id.btn_reapply:{
                break;
            }
            case R.id.btn_cancel:{
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
}
