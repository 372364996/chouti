package com.hanzhi.onlineclassroom.ui.selectclass.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.bean.selectclass.ClassBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.selectclass.contract.ClassDetailContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 14:24
 */
public class ClassDetailModel extends BaseModelImpl implements ClassDetailContract.Model {
    @Override
    public void getClassDetailData(int classId, ClassDetailContract.OnGetClassDetailDataListener listener) {
        String url = Constants.GET_CLASS_DETAIL_LIST;
        Map<String, String> map = new HashMap<>();
        map.put("classId", String.valueOf(classId));
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetClassDetailDataFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    ClassBean classBean = JSONObject.parseObject(resultBean.getData(), ClassBean.class);
                    listener.onGetClassDetailDataSuccess(classBean);
                }
            }
        });
        disposableList.add(disposable);
    }
}
