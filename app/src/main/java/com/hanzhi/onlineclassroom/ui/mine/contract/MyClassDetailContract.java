package com.hanzhi.onlineclassroom.ui.mine.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/2 16:31
 */
public interface MyClassDetailContract {
    interface Model {
        void getMyClassDetailData(String orderId, OnGetMyClassDetailDataListener listener);
        void submitAppraise(String orderId, float ranking, String content, OnSubmitAppraiseListener listener);
    }
    interface OnGetMyClassDetailDataListener{
        void onGetMyClassDetailDataSuccess(long remainingTime);
        void onGetMyClassDetailDataFailure(String message);
    }
    interface OnSubmitAppraiseListener{
        void onSubmitAppraiseSuccess(String message);
        void onSubmitAppraiseFailure(String message);
    }
    interface View extends BaseContract.View {
        void setRemainingTime(long remainingTime);
    }

    interface Presenter {
        void getMyClassDetailData(String orderId);
        void submitAppraise(String orderId, float ranking, String content);
    }
}
