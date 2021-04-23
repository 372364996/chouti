package com.hanzhi.onlineclassroom;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.chewawa.baselibrary.BaseApplication;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.utils.CommonUtil;
import com.hanzhi.onlineclassroom.utils.SysApplicationUtils;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/5 14:18
 */
public class SysApplication extends BaseApplication {
    protected SysApplicationUtils applicationUtils;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        applicationUtils = new SysApplicationUtils(this);
        String baseUrl = CommonUtil.getDomainName(Constants.BASE_IP);
        applicationUtils.initHttpClient(baseUrl, null);
    }

    @Override
    public void exitLogin(boolean isShowDialog, String message) {
        if(isShowDialog){
//            LoginActivity.start(BaseApplication.getTopActivity(), LoginActivity.FROM_ERROR_LOG_OUT, message);
        }else {

        }
    }

    @Override
    public Activity getTopActivity() {
        return super.getTopActivity();
    }
}
