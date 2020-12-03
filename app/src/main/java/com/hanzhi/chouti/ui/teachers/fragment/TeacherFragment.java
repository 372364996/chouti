package com.hanzhi.chouti.ui.teachers.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.teachers.TeacherBean;
import com.hanzhi.chouti.event.RefreshTeacherListEvent;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.selectclass.ClassApplyActivity;
import com.hanzhi.chouti.ui.selectclass.SelectClassActivity;
import com.hanzhi.chouti.ui.teachers.TeacherInfoActivity;
import com.hanzhi.chouti.ui.teachers.adapter.TeacherAdapter;
import com.hanzhi.chouti.ui.teachers.contract.TeacherListContract;
import com.hanzhi.chouti.ui.teachers.presenter.TeacherInfoPresenter;
import com.hanzhi.chouti.ui.teachers.presenter.TeacherListPresenter;
import com.hanzhi.chouti.utils.RequestParamsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class TeacherFragment extends BaseRecycleViewFragment<TeacherBean> implements TeacherListContract.View {
    ClassApplyBean classApplyBean;
    TeacherListPresenter teacherListPresenter;
    public static TeacherFragment newInstance(ClassApplyBean classApplyBean) {
        TeacherFragment teacherFragment = new TeacherFragment();
        Bundle args = new Bundle();
        args.putParcelable("classApplyBean", classApplyBean);
        teacherFragment.setArguments(args);
        return teacherFragment;
    }
    @Override
    protected String getUrl() {
        return Constants.GET_TEACHER_LIST_URL;
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
    protected Class<TeacherBean> getResultClass() {
        return TeacherBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter getAdapter() {
        return new TeacherAdapter();
    }

    @Override
    public BasePresenterImpl initPresenter() {
        teacherListPresenter = new TeacherListPresenter(this);
        return super.initPresenter();
    }

    @Override
    protected View addHeaderView() {
        headerView = inflater.inflate(R.layout.teacherlistviewheader, null);
        return headerView;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemChildClick(adapter, view, position);
        TeacherBean teacherBean = (TeacherBean) adapter.getItem(position);
        if(teacherBean == null){
            return;
        }
        teacherListPresenter.collectTeacher(position, teacherBean.getUserId(), !teacherBean.isFans());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        Log.e("整体item----->", position + "");
        TeacherBean teacherBean = (TeacherBean) adapter.getItem(position);
        if(teacherBean == null){
            return;
        }
        if(classApplyBean == null){
            classApplyBean = new ClassApplyBean();
        }
        classApplyBean.setTeacherId(teacherBean.getUserId());
        classApplyBean.setTeacherName(teacherBean.getName());
        if(TextUtils.isEmpty(classApplyBean.getDateTimeStr())){
            TeacherInfoActivity.start(getActivity(), classApplyBean, TeacherInfoActivity.FROM_TEACHER_LIST);
        }else if(classApplyBean.getClassId() == 0){
            SelectClassActivity.start(getActivity(), classApplyBean);
        }else {
            ClassApplyActivity.start(getActivity(), classApplyBean);
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshTeacherListEvent event) {
        onRefresh();
    }

    @Override
    public void collectSuccess(int position, boolean isFans) {
        ((TeacherBean)baseRecycleViewAdapter.getData().get(position)).setFans(isFans);
        baseRecycleViewAdapter.notifyItemChanged(position+baseRecycleViewAdapter.getHeaderLayoutCount());
    }
}

