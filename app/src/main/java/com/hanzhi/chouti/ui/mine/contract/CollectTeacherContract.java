package com.hanzhi.chouti.ui.mine.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.chouti.ui.teachers.contract.TeacherListContract;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/3 17:10
 */
public interface CollectTeacherContract {

    interface View extends BaseContract.View {
        void collectSuccess(int position, boolean isFans);
    }

    interface Presenter {
        void collectTeacher(int position, int teacherId, boolean isFans);
    }
}
