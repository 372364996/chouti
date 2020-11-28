package com.hanzhi.chouti.ui.selectclass.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.selectclass.ClassBean;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 16:53
 */
public interface ClassApplyContract {
    interface Model {
        void getClassApplyData(int classId, int teacherId, String dateTime, OnGetClassApplyDataListener listener);
    }
    interface OnGetClassApplyDataListener{
        void onGetClassApplyDataSuccess(ClassBean classBean);
        void onGetClassApplyDataFailure(String message);
    }

    interface View extends BaseContract.View {
        void setClassApplyData(ClassBean classBean);
    }

    interface Presenter {
        void getClassApplyData(ClassApplyBean classApplyBean);
    }
}
