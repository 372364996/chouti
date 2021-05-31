package com.hanzhi.onlineclassroom.ui.appointment.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.onlineclassroom.bean.appointment.AppointmentTabBean;

import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 18:39
 */
public interface AppointmentTimeContract {
    interface Model {
        void getTabList(OnGetTabListListener listener);
    }
    interface OnGetTabListListener{
        void onGetTabListSuccess(List<AppointmentTabBean> list);
        void onGetTabListFailure(String message);
    }

    interface View extends BaseContract.View {
        void setTabList(List<AppointmentTabBean> list, List<String> titleList);
    }

    interface Presenter {
        void getTabList();
    }
}
