package com.hanzhi.chouti.ui.mine.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.mine.contract.MyClassDetailContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/2 16:31
 */
public class MyClassDetailModel extends BaseModelImpl implements MyClassDetailContract.Model {
    @Override
    public void getMyClassDetailData(String orderId, MyClassDetailContract.OnGetMyClassDetailDataListener listener) {
        String url = Constants.GET_MY_CLASS_DETAIL_URL;
        Map<String, String> map = new HashMap<>();
        map.put("number", orderId);
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetMyClassDetailDataFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    Long remainingTime = JSONObject.parseObject(resultBean.getData(), Long.class);
                    listener.onGetMyClassDetailDataSuccess(remainingTime);
                }
            }
        });
        disposableList.add(disposable);
    }

    @Override
    public void submitAppraise(String orderId, float ranking, String content, MyClassDetailContract.OnSubmitAppraiseListener listener) {
        String url = Constants.SUBMIT_CLASS_APPRAISE_URL;
        Map<String, String> map = new HashMap<>();
        map.put("number", orderId);
        map.put("score", String.valueOf((int)ranking));
        map.put("content", content);
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onSubmitAppraiseFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                listener.onSubmitAppraiseSuccess(resultBean.getMsg());
            }
        });
        disposableList.add(disposable);
    }
}
