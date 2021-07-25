package com.hanzhi.onlineclassroom.ui.mine.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.onlineclassroom.bean.mine.UsInfoBean;

public interface MyProfileContract {
    interface Model {
        void getUserInfo(WalletContract.OnGetUsInfoListener listener);
    }

    interface OnGetUserInfoListener {
        void onGetUserInfoSuccess(UsInfoBean usInfoBean);

        void onGetUserInfoFailure(String message);
    }

    interface View extends BaseContract.View {
        void setUserInfo(UsInfoBean usInfoBean);
    }

    interface Presenter {
        void getUserInfo();
    }
}
