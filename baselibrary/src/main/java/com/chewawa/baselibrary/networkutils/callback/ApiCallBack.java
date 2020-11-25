package com.chewawa.baselibrary.networkutils.callback;


import com.chewawa.baselibrary.networkutils.bean.ResultBean;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2019/7/17 15:12
 */
public interface ApiCallBack {
    void onError(int status, String message);

    void onSuccess(ResultBean resultBean);
}
