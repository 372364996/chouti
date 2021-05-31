package com.hanzhi.onlineclassroom.ui.selectclass.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.onlineclassroom.bean.ClassApplyBean;
import com.hanzhi.onlineclassroom.bean.mine.ClassCardBean;

import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 16:53
 */
public interface ClassApplyContract {
    interface Model {
        void getClassApplyData(int classId, int teacherId, String dateTime, OnGetClassApplyDataListener listener);
        void getClassCardList(OnGetClassCardListListener listener);
        void submitClassApply(ClassApplyBean classApplyBean, int classCardId, OnSubmitClassApplyListener listener);
    }
    interface OnGetClassApplyDataListener{
        void onGetClassApplyDataSuccess(String message);
        void onGetClassApplyDataFailure(String message);
    }
    interface OnGetClassCardListListener{
        void onGetClassCardListSuccess(List<ClassCardBean> list);
        void onGetClassCardListFailure(String message);
    }
    interface OnSubmitClassApplyListener{
        void onSubmitClassApplySuccess(String message);
        void onSubmitClassApplyFailure(String message);
    }
    interface View extends BaseContract.View {
        void setClassApplyData(String message);
        void setClassCardList(List<ClassCardBean> list);
        void submitClassApplySuccess();
    }

    interface Presenter {
        void getClassApplyData(ClassApplyBean classApplyBean);
        void getClassCardList();
        void submitClassApply(ClassApplyBean classApplyBean, int classCardId);
    }
}
