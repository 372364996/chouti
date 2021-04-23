package com.hanzhi.onlineclassroom.ui.teachers.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/3 16:20
 */
public interface TeacherListContract {
    interface Model {
        void collectTeacher(int teacherId, boolean isFans, OnCollectTeacherListener listener);
    }
    interface OnCollectTeacherListener{
        void onCollectTeacherSuccess(boolean isFans);
        void onCollectTeacherFailure(String message);
    }
    interface View extends BaseContract.View {
        void collectSuccess(int position, boolean isFans);
    }

    interface Presenter {
        void collectTeacher(int position, int teacherId, boolean isFans);
    }
}
