package com.hanzhi.onlineclassroom.ui.selectclass.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.onlineclassroom.bean.selectclass.ClassBean;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 14:24
 */
public interface ClassDetailContract {
    interface Model {
        void getClassDetailData(int classId, OnGetClassDetailDataListener listener);
    }
    interface OnGetClassDetailDataListener{
        void onGetClassDetailDataSuccess(ClassBean classBean);
        void onGetClassDetailDataFailure(String message);
    }

    interface View extends BaseContract.View {
        void setClassDetailData(ClassBean classBean);
    }

    interface Presenter {
        void getClassDetailData(int classId);
    }
}
