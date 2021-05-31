package com.hanzhi.onlineclassroom.ui.mine.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.onlineclassroom.bean.mine.MyClassTabBean;

import java.util.List;

public interface MyClassContract {
    interface Model {
        void getTabList(OnGetTabListListener listener);
        void cancelClass(String orderId, OnCancelClassListener listener);
        void joinClass(String orderId, OnJoinClassListener listener);
    }
    interface OnGetTabListListener{
        void onGetTabListSuccess(List<MyClassTabBean> list);
        void onGetTabListFailure(String message);
    }
    interface OnCancelClassListener{
        void onCancelClassSuccess(String message);
        void onCancelClassFailure(String message);
    }
    interface OnJoinClassListener{
        void onJoinClassListSuccess(String message);
        void onJoinClassListFailure(String message);
    }
    interface View extends BaseContract.View {
        void setTabList(List<MyClassTabBean> list, List<String> titleList);
        void refreshList();
        void joinClassSuccess();
        void joinClassTips(String message);
    }

    interface Presenter {
        void getTabList();
        void cancelClass(String orderId);
        void joinClass(String orderId);
    }
}
