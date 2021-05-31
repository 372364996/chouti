package com.hanzhi.onlineclassroom.ui.mine.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.onlineclassroom.ui.mine.contract.CollectTeacherContract;
import com.hanzhi.onlineclassroom.ui.teachers.contract.TeacherListContract;
import com.hanzhi.onlineclassroom.ui.teachers.model.TeacherListModel;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/3 17:10
 */
public class CollectTeacherPresenter extends BasePresenterImpl<CollectTeacherContract.View, TeacherListModel> implements CollectTeacherContract.Presenter, TeacherListContract.OnCollectTeacherListener {
    int position;
    public CollectTeacherPresenter(CollectTeacherContract.View view) {
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
