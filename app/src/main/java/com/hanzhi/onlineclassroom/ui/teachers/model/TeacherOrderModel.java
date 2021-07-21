package com.hanzhi.onlineclassroom.ui.teachers.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.bean.mine.MyClassTabBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.mine.contract.MyClassContract;
import com.hanzhi.onlineclassroom.ui.teachers.contract.TeacherOrderContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class TeacherOrderModel extends BaseModelImpl implements TeacherOrderContract.Model {
    @Override
    public void getTabList(MyClassContract.OnGetTabListListener listener) {
        String url = Constants.GET_MY_CLASS_TAB_URL;
        Map<String, String> map = new HashMap<>();
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetTabListFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    List<MyClassTabBean> list = JSONObject.parseArray(resultBean.getData(), MyClassTabBean.class);
                    listener.onGetTabListSuccess(list);
                }
            }
        });
        disposableList.add(disposable);
    }

    @Override
    public void cancelClass(String orderId, MyClassContract.OnCancelClassListener listener) {
        String url = Constants.USER_CANCEL_CLASS_APPLY;
        Map<String, String> map = new HashMap<>();
        map.put("number", orderId);
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onCancelClassFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                listener.onCancelClassSuccess(resultBean.getMsg());
            }
        });
        disposableList.add(disposable);
    }

    @Override
    public void joinClass(String orderId, MyClassContract.OnJoinClassListener listener) {
        String url = Constants.JOIN_CLASS_APPLY;
        Map<String, String> map = new HashMap<>();
        map.put("number", orderId);
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onJoinClassListFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                listener.onJoinClassListSuccess(resultBean.getMsg());
            }
        });
        disposableList.add(disposable);
    }
}
