package com.hanzhi.chouti.ui.selectclass.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.selectclass.ClassBean;
import com.hanzhi.chouti.ui.selectclass.contract.ClassApplyContract;
import com.hanzhi.chouti.ui.selectclass.model.ClassApplyModel;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 16:53
 */
public class ClassApplyPresenter extends BasePresenterImpl<ClassApplyContract.View, ClassApplyModel> implements ClassApplyContract.Presenter, ClassApplyContract.OnGetClassApplyDataListener {
    public ClassApplyPresenter(ClassApplyContract.View view) {
        super(view);
    }

    @Override
    public ClassApplyModel initModel() {
        return new ClassApplyModel();
    }

    @Override
    public void getClassApplyData(ClassApplyBean classApplyBean) {
        int classId = classApplyBean.getClassId();
        int teacherId = classApplyBean.getTeacherId();
        String dateTime = classApplyBean.getDateTimeStr();
        model.getClassApplyData(classId, teacherId, dateTime, this);
    }

    @Override
    public void onGetClassApplyDataSuccess(ClassBean classBean) {
        view.hideProgressDialog();
        if(classBean == null){
            return;
        }
        view.setClassApplyData(classBean);
    }

    @Override
    public void onGetClassApplyDataFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
