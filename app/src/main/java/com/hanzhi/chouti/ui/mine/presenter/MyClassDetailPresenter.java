package com.hanzhi.chouti.ui.mine.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.chouti.ui.mine.contract.MyClassDetailContract;
import com.hanzhi.chouti.ui.mine.model.MyClassDetailModel;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/2 16:31
 */
public class MyClassDetailPresenter extends BasePresenterImpl<MyClassDetailContract.View, MyClassDetailModel> implements MyClassDetailContract.Presenter, MyClassDetailContract.OnGetMyClassDetailDataListener, MyClassDetailContract.OnSubmitAppraiseListener {
    public MyClassDetailPresenter(MyClassDetailContract.View view) {
        super(view);
    }

    @Override
    public MyClassDetailModel initModel() {
        return new MyClassDetailModel();
    }

    @Override
    public void getMyClassDetailData(String orderId) {
        model.getMyClassDetailData(orderId, this);
    }

    @Override
    public void submitAppraise(String orderId, float ranking, String content) {
        view.showProgressDialog();
        model.submitAppraise(orderId, ranking, content, this);
    }

    @Override
    public void onGetMyClassDetailDataSuccess(long remainingTime) {
        view.hideProgressDialog();
        view.setRemainingTime(remainingTime);
    }

    @Override
    public void onGetMyClassDetailDataFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }

    @Override
    public void onSubmitAppraiseSuccess(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }

    @Override
    public void onSubmitAppraiseFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
