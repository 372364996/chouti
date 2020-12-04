package com.hanzhi.chouti.ui.mine.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.chouti.bean.mine.MyClassTabBean;
import com.hanzhi.chouti.bean.mine.UsInfoBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.mine.contract.WalletContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/4 14:03
 */
public class WalletModel extends BaseModelImpl implements WalletContract.Model {
    @Override
    public void getUsInfo(WalletContract.OnGetUsInfoListener listener) {
        String url = Constants.GET_US_INFO_APPLY;
        Map<String, String> map = new HashMap<>();
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetUsInfoFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    UsInfoBean usInfoBean = JSONObject.parseObject(resultBean.getData(), UsInfoBean.class);
                    listener.onGetUsInfoSuccess(usInfoBean);
                }
            }
        });
        disposableList.add(disposable);
    }
}
