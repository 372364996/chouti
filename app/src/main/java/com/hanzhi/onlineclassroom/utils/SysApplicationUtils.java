package com.hanzhi.onlineclassroom.utils;

import android.app.Application;

import com.chewawa.baselibrary.networkutils.HttpClientUtil;
import com.chewawa.baselibrary.utils.BaseApplicationUtils;
import com.hanzhi.onlineclassroom.network.interceptor.CustomSignInterceptor;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/31 15:24
 */
public class SysApplicationUtils extends BaseApplicationUtils {

    public SysApplicationUtils(Application application) {
        super(application);
    }

    @Override
    public void initHttpClient(String baseUrl, String sourceCode) {
        HttpClientUtil.getInstance().addInterceptor(new CustomSignInterceptor(sourceCode)).init(mApplication, baseUrl, sourceCode);
    }

}
