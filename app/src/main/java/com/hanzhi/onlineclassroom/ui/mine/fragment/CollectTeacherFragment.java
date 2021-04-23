package com.hanzhi.onlineclassroom.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.hanzhi.onlineclassroom.bean.ClassApplyBean;
import com.hanzhi.onlineclassroom.bean.teachers.TeacherBean;
import com.hanzhi.onlineclassroom.event.RefreshCollectTeacherEvent;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.mine.contract.CollectTeacherContract;
import com.hanzhi.onlineclassroom.ui.mine.presenter.CollectTeacherPresenter;
import com.hanzhi.onlineclassroom.ui.teachers.TeacherInfoActivity;
import com.hanzhi.onlineclassroom.ui.teachers.adapter.TeacherAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

/**
 * @class 收藏老师
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class CollectTeacherFragment extends BaseRecycleViewFragment<TeacherBean> implements CollectTeacherContract.View {
    ClassApplyBean classApplyBean;
    CollectTeacherPresenter collectTeacherPresenter;
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
        collectTeacherPresenter = new CollectTeacherPresenter(this);
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemChildClick(adapter, view, position);
        TeacherBean teacherBean = (TeacherBean) adapter.getItem(position);
        if(teacherBean == null){
            return;
        }
        collectTeacherPresenter.collectTeacher(position, teacherBean.getUserId(), !teacherBean.isFans());
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

    @Override
    public void collectSuccess(int position, boolean isFans) {
        ((TeacherBean)baseRecycleViewAdapter.getData().get(position)).setFans(isFans);
        baseRecycleViewAdapter.notifyItemChanged(position);
    }
}
