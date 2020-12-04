package com.hanzhi.chouti.ui.mine.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.chouti.bean.mine.UsInfoBean;
import com.hanzhi.chouti.ui.mine.contract.WalletContract;
import com.hanzhi.chouti.ui.mine.model.WalletModel;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/4 14:03
 */
public class WalletPresenter extends BasePresenterImpl<WalletContract.View, WalletModel> implements WalletContract.Presenter, WalletContract.OnGetUsInfoListener {
    public WalletPresenter(WalletContract.View view) {
        super(view);
    }

    @Override
    public WalletModel initModel() {
        return new WalletModel();
    }

    @Override
    public void getUsInfo() {
        model.getUsInfo(this);

    }

    @Override
    public void onGetUsInfoSuccess(UsInfoBean usInfoBean) {
        view.hideProgressDialog();
        if(usInfoBean == null){
            return;
        }
        view.setUserInfo(usInfoBean);
    }

    @Override
    public void onGetUsInfoFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
