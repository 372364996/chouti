package com.hanzhi.onlineclassroom.ui.appointment.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.onlineclassroom.bean.appointment.AppointmentTabBean;
import com.hanzhi.onlineclassroom.ui.appointment.contract.AppointmentTimeContract;
import com.hanzhi.onlineclassroom.ui.appointment.contract.SetAppointmentTimeContract;
import com.hanzhi.onlineclassroom.ui.appointment.model.AppointmentTimeModel;
import com.hanzhi.onlineclassroom.ui.appointment.model.SetAppointmentTimeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 18:39
 */
public class SetAppointmentTimePresenter extends BasePresenterImpl<SetAppointmentTimeContract.View, SetAppointmentTimeModel> implements SetAppointmentTimeContract.Presenter, SetAppointmentTimeContract.OnGetTabListListener {
    public SetAppointmentTimePresenter(SetAppointmentTimeContract.View view) {
        super(view);
    }

    @Override
    public SetAppointmentTimeModel initModel() {
        return new SetAppointmentTimeModel();
    }

    @Override
    public void getTabList() {
        model.getTabList(this);

    }

    @Override
    public void onGetTabListSuccess(List<AppointmentTabBean> list) {
        view.hideProgressDialog();
        if(list == null){
            return;
        }
        view.setTabList(list, getTitleList(list));
    }
    public List<String> getTitleList(List<AppointmentTabBean> list){
        List<String> titleList = new ArrayList<>();
        for(int i = 0; i<list.size()-7; i++){
            titleList.add(list.get(i).getWeek());
        }
        return titleList;
    }

    @Override
    public void onGetTabListFailure(String message) {
        ToastUtils.showToast(message);
        view.hideProgressDialog();

    }
}
