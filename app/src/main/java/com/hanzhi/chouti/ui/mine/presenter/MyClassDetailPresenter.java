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
public class MyClassDetailPresenter extends BasePresenterImpl<MyClassDetailContract.View, MyClassDetailModel> implements MyClassDetailContract.Presenter, MyClassDetailContract.OnGetClassDetailDataListener {
    public MyClassDetailPresenter(MyClassDetailContract.View view) {
        super(view);
    }

    @Override
    public MyClassDetailModel initModel() {
        return new MyClassDetailModel();
    }

    @Override
    public void getClassDetailData(String orderId) {
        model.getClassDetailData(orderId, this);
    }

    @Override
    public void onGetClassDetailDataSuccess(long remainingTime) {
        view.hideProgressDialog();
        view.setRemainingTime(remainingTime);
    }

    @Override
    public void onGetClassDetailDataFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
