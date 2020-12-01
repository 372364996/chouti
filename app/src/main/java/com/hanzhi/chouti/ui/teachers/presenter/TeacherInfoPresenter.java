package com.hanzhi.chouti.ui.teachers.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.chouti.bean.teachers.TeacherBean;
import com.hanzhi.chouti.ui.teachers.contract.TeacherInfoContract;
import com.hanzhi.chouti.ui.teachers.model.TeacherInfoModel;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/27 17:21
 */
public class TeacherInfoPresenter extends BasePresenterImpl<TeacherInfoContract.View, TeacherInfoModel> implements TeacherInfoContract.Presenter, TeacherInfoContract.OnGetTeacherInfoListener, TeacherInfoContract.OnCollectTeacherListener {
    public TeacherInfoPresenter(TeacherInfoContract.View view) {
        super(view);
    }

    @Override
    public TeacherInfoModel initModel() {
        return new TeacherInfoModel();
    }

    @Override
    public void getTeacherInfo(int teacherId) {
        model.getTeacherInfo(teacherId, this);
    }

    @Override
    public void collectTeacher(int teacherId, boolean isFans) {
        view.showProgressDialog();
        model.collectTeacher(teacherId, isFans, this);
    }

    @Override
    public void onGetTeacherInfoSuccess(TeacherBean teacherBean) {
        if(teacherBean == null){
            return;
        }
        view.setTeacherInfo(teacherBean);
    }

    @Override
    public void onGetTeacherInfoFailure(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void onCollectTeacherSuccess(boolean isFans) {
        view.hideProgressDialog();
        view.collectSuccess(isFans);
    }

    @Override
    public void onCollectTeacherFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
