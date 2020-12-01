package com.hanzhi.chouti.ui.teachers.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.chouti.bean.teachers.TeacherBean;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/27 17:21
 */
public interface TeacherInfoContract {
    interface Model {
        void getTeacherInfo(int teacherId, OnGetTeacherInfoListener listener);
        void collectTeacher(int teacherId, boolean isFans, OnCollectTeacherListener listener);
    }
    interface OnGetTeacherInfoListener{
        void onGetTeacherInfoSuccess(TeacherBean teacherBean);
        void onGetTeacherInfoFailure(String message);
    }
    interface OnCollectTeacherListener{
        void onCollectTeacherSuccess(boolean isFans);
        void onCollectTeacherFailure(String message);
    }
    interface View extends BaseContract.View {
        void setTeacherInfo(TeacherBean teacherBean);
        void collectSuccess(boolean isFans);
    }

    interface Presenter {
        void getTeacherInfo(int teacherId);
        void collectTeacher(int teacherId, boolean isFans);
    }
}
