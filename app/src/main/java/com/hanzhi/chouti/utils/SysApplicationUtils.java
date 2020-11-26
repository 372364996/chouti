package com.hanzhi.chouti.utils;

import android.app.Application;
import android.app.Notification;
import android.content.Context;

import com.chewawa.baselibrary.networkutils.HttpClientUtil;
import com.chewawa.baselibrary.networkutils.event.NewMessageNumEvent;
import com.chewawa.baselibrary.utils.BaseApplicationUtils;
import com.chewawa.baselibrary.utils.LogUtils;
import com.hanzhi.chouti.network.interceptor.CustomSignInterceptor;

import org.greenrobot.eventbus.EventBus;

import me.leolin.shortcutbadger.ShortcutBadger;

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
