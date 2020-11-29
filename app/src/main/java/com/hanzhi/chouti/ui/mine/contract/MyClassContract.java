package com.hanzhi.chouti.ui.mine.contract;

import com.chewawa.baselibrary.base.contract.BaseContract;
import com.hanzhi.chouti.bean.mine.MyClassTabBean;

import java.util.List;

public interface MyClassContract {
    interface Model {
        void getTabList(OnGetTabListListener listener);
    }
    interface OnGetTabListListener{
        void onGetTabListSuccess(List<MyClassTabBean> list);
        void onGetTabListFailure(String message);
    }

    interface View extends BaseContract.View {
        void setTabList(List<MyClassTabBean> list, List<String> titleList);
    }

    interface Presenter {
        void getTabList();
    }
}
