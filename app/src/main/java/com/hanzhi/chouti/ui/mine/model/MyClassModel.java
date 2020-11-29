package com.hanzhi.chouti.ui.mine.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.chouti.bean.mine.MyClassTabBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.mine.contract.MyClassContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class MyClassModel extends BaseModelImpl implements MyClassContract.Model {
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
}
