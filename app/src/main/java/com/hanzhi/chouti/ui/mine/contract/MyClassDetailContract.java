package com.hanzhi.chouti.ui.mine.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/2 16:31
 */
public interface MyClassDetailContract {
    interface Model {
        void getClassDetailData(String orderId, OnGetClassDetailDataListener listener);
    }
    interface OnGetClassDetailDataListener{
        void onGetClassDetailDataSuccess(long remainingTime);
        void onGetClassDetailDataFailure(String message);
    }

    interface View extends BaseContract.View {
        void setRemainingTime(long remainingTime);
    }

    interface Presenter {
        void getClassDetailData(String orderId);
    }
}
