package com.hanzhi.chouti.ui.mine.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.mine.MyClassBean;
import com.hanzhi.chouti.bean.mine.MyClassTabBean;
import com.hanzhi.chouti.bean.teachers.TeacherBean;
import com.hanzhi.chouti.event.RefreshCollectTeacherEvent;
import com.hanzhi.chouti.event.RefreshTeacherListEvent;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.appointment.AppointmentTimeActivity;
import com.hanzhi.chouti.ui.mine.MyClassDetailActivity;
import com.hanzhi.chouti.ui.mine.adapter.MyClassAdapter;
import com.hanzhi.chouti.ui.mine.contract.MyClassContract;
import com.hanzhi.chouti.ui.mine.presenter.MyClassPresenter;
import com.hanzhi.chouti.ui.selectclass.ClassApplyActivity;
import com.hanzhi.chouti.ui.selectclass.SelectClassActivity;
import com.hanzhi.chouti.ui.teachers.TeacherInfoActivity;
import com.hanzhi.chouti.ui.teachers.adapter.TeacherAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class CollectTeacherFragment extends BaseRecycleViewFragment<TeacherBean>{
    ClassApplyBean classApplyBean;
    public static CollectTeacherFragment newInstance() {
        CollectTeacherFragment collectTeacherFragment = new CollectTeacherFragment();
        Bundle args = new Bundle();
        collectTeacherFragment.setArguments(args);
        return collectTeacherFragment;
    }
    @Override
    protected String getUrl() {
        return Constants.GET_MY_TEACHER_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.put("size", 20);
        return params;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public BasePresenterImpl initPresenter() {
        return super.initPresenter();
    }

    @Override
    protected Class<TeacherBean> getResultClass() {
        return TeacherBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter<TeacherBean> getAdapter() {
        return new TeacherAdapter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        TeacherBean teacherBean = (TeacherBean) adapter.getItem(position);
        if(teacherBean == null){
            return;
        }
        if(classApplyBean == null){
            classApplyBean = new ClassApplyBean();
        }
        classApplyBean.setTeacherId(teacherBean.getUserId());
        classApplyBean.setTeacherName(teacherBean.getName());
        TeacherInfoActivity.start(getActivity(), classApplyBean, TeacherInfoActivity.FROM_COLLECT_LIST);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshCollectTeacherEvent event) {
        onRefresh();
    }
}
