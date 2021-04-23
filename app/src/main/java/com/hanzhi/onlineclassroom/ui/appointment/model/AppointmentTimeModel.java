package com.hanzhi.onlineclassroom.ui.appointment.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.bean.appointment.AppointmentTabBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.appointment.contract.AppointmentTimeContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 18:39
 */
public class AppointmentTimeModel extends BaseModelImpl implements AppointmentTimeContract.Model {
    @Override
    public void getTabList(AppointmentTimeContract.OnGetTabListListener listener) {
        String url = Constants.GET_CLASS_TIME_TAB_URL;
        Map<String, Object> map = new HashMap<>();
        Disposable disposable = HttpManager.post(url).upJsonObject(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetTabListFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    List<AppointmentTabBean> list = JSONObject.parseArray(resultBean.getData(), AppointmentTabBean.class);
                    listener.onGetTabListSuccess(list);
                }
            }
        });
        disposableList.add(disposable);
    }
}
