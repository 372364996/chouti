package com.hanzhi.onlineclassroom.ui.mine.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.onlineclassroom.bean.mine.UsInfoBean;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/4 14:03
 */
public interface WalletContract {
    interface Model {
        void getUsInfo(OnGetUsInfoListener listener);
    }
    interface OnGetUsInfoListener{
        void onGetUsInfoSuccess(UsInfoBean usInfoBean);
        void onGetUsInfoFailure(String message);
    }

    interface View extends BaseContract.View {
        void setUserInfo(UsInfoBean usInfoBean);
    }

    interface Presenter {
        void getUsInfo();
    }
}
