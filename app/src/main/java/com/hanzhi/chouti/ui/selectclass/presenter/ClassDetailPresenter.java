package com.hanzhi.chouti.ui.selectclass.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.chouti.bean.selectclass.ClassBean;
import com.hanzhi.chouti.ui.selectclass.contract.ClassDetailContract;
import com.hanzhi.chouti.ui.selectclass.model.ClassDetailModel;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 14:24
 */
public class ClassDetailPresenter extends BasePresenterImpl<ClassDetailContract.View, ClassDetailModel> implements ClassDetailContract.Presenter, ClassDetailContract.OnGetClassDetailDataListener {
    public ClassDetailPresenter(ClassDetailContract.View view) {
        super(view);
    }

    @Override
    public ClassDetailModel initModel() {
        return new ClassDetailModel();
    }

    @Override
    public void getClassDetailData(int classId) {
        model.getClassDetailData(classId, this);
    }

    @Override
    public void onGetClassDetailDataSuccess(ClassBean classBean) {
        view.hideProgressDialog();
        if(classBean == null){
            return;
        }
        view.setClassDetailData(classBean);
    }

    @Override
    public void onGetClassDetailDataFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
