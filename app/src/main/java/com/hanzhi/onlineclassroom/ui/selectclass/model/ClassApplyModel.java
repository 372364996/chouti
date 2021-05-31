package com.hanzhi.onlineclassroom.ui.selectclass.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.bean.ClassApplyBean;
import com.hanzhi.onlineclassroom.bean.mine.ClassCardBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.selectclass.contract.ClassApplyContract;
import com.hanzhi.onlineclassroom.utils.RequestParamsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 16:53
 */
public class ClassApplyModel extends BaseModelImpl implements ClassApplyContract.Model {
    @Override
    public void getClassApplyData(int classId, int teacherId, String dateTime, ClassApplyContract.OnGetClassApplyDataListener listener) {
        String url = Constants.GET_CLASS_APPLY_TIPS;
        Map<String, String> map = new HashMap<>();
        map.put("classId", String.valueOf(classId));
        map.put("teacherId", String.valueOf(teacherId));
        map.put("classTime", dateTime);
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetClassApplyDataFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    listener.onGetClassApplyDataSuccess(resultBean.getData());
                }
            }
        });
        disposableList.add(disposable);
    }

    @Override
    public void getClassCardList(ClassApplyContract.OnGetClassCardListListener listener) {
        String url = Constants.GET_CLASS_CARD_LIST;
        Map<String, String> map = new HashMap<>();
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetClassCardListFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    List<ClassCardBean> list = JSONObject.parseArray(resultBean.getData(), ClassCardBean.class);
                    listener.onGetClassCardListSuccess(list);
                }
            }
        });
        disposableList.add(disposable);
    }

    @Override
    public void submitClassApply(ClassApplyBean classApplyBean, int classCardId, ClassApplyContract.OnSubmitClassApplyListener listener) {
        String url = Constants.SUBMIT_CLASS_APPLY;
        Map<String, String> map = new HashMap<>();
        map.putAll(RequestParamsUtils.getClassApplyParams(classApplyBean));
        map.put("classCardOrderId", String.valueOf(classCardId));
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onSubmitClassApplyFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                listener.onSubmitClassApplySuccess(resultBean.getMsg());
            }
        });
        disposableList.add(disposable);
    }
}
