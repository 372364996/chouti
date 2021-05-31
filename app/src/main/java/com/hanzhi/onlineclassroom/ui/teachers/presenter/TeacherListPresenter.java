package com.hanzhi.onlineclassroom.ui.teachers.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.onlineclassroom.ui.teachers.contract.TeacherListContract;
import com.hanzhi.onlineclassroom.ui.teachers.model.TeacherListModel;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/3 16:20
 */
public class TeacherListPresenter extends BasePresenterImpl<TeacherListContract.View, TeacherListModel> implements TeacherListContract.Presenter, TeacherListContract.OnCollectTeacherListener {
    int position;
    public TeacherListPresenter(TeacherListContract.View view) {
        super(view);
    }

    @Override
    public TeacherListModel initModel() {
        return new TeacherListModel();
    }

    @Override
    public void collectTeacher(int position, int teacherId, boolean isFans) {
        this.position = position;
        view.showProgressDialog();
        model.collectTeacher(teacherId, isFans, this);
    }

    @Override
    public void onCollectTeacherSuccess(boolean isFans) {
        view.hideProgressDialog();
        view.collectSuccess(position, isFans);
    }

    @Override
    public void onCollectTeacherFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
