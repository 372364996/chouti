package com.hanzhi.chouti;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.chewawa.baselibrary.BaseApplication;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/5 14:18
 */
public class SysApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
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
