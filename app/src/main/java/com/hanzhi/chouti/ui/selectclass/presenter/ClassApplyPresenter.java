package com.hanzhi.chouti.ui.selectclass.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.mine.ClassCardBean;
import com.hanzhi.chouti.ui.selectclass.contract.ClassApplyContract;
import com.hanzhi.chouti.ui.selectclass.model.ClassApplyModel;

import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 16:53
 */
public class ClassApplyPresenter extends BasePresenterImpl<ClassApplyContract.View, ClassApplyModel> implements ClassApplyContract.Presenter, ClassApplyContract.OnGetClassApplyDataListener, ClassApplyContract.OnGetClassCardListListener, ClassApplyContract.OnSubmitClassApplyListener {
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
    public void getClassCardList() {
        view.showProgressDialog();
        model.getClassCardList(this);
    }

    @Override
    public void submitClassApply(ClassApplyBean classApplyBean, int classCardId) {
        if(classApplyBean == null){
            return;
        }
        if(classCardId == 0){
            return;
        }
        model.submitClassApply(classApplyBean, classCardId, this);
    }

    @Override
    public void onGetClassApplyDataSuccess(String message) {
        view.setClassApplyData(message);
    }

    @Override
    public void onGetClassApplyDataFailure(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void onGetClassCardListSuccess(List<ClassCardBean> list) {
        view.hideProgressDialog();
        if(list == null){
            return;
        }
        view.setClassCardList(list);
    }

    @Override
    public void onGetClassCardListFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }

    @Override
    public void onSubmitClassApplySuccess(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
        view.submitClassApplySuccess();
    }

    @Override
    public void onSubmitClassApplyFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
