package com.hanzhi.onlineclassroom.ui.selectclass.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.onlineclassroom.bean.selectclass.ClassTabBean;

import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 18:39
 */
public interface SelectClassContract {
    interface Model {
        void getTabList(OnGetTabListListener listener);
    }
    interface OnGetTabListListener{
        void onGetTabListSuccess(List<ClassTabBean> list);
        void onGetTabListFailure(String message);
    }

    interface View extends BaseContract.View {
        void setTabList(List<ClassTabBean> list, List<String> titleList);
    }

    interface Presenter {
        void getTabList();
    }
}
