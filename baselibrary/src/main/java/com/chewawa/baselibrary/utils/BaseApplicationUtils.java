package com.chewawa.baselibrary.utils;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.bean.AppUpdateEntity;
import com.chewawa.baselibrary.networkutils.HttpClientUtil;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.utils.updateplugin.CustomDownloadWorker;
import com.chewawa.baselibrary.utils.updateplugin.CustomFileChecker;
import com.chewawa.baselibrary.utils.updateplugin.CustomFileCreator;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.base.UpdateStrategy;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/13 15:56
 */
public class BaseApplicationUtils {
    protected Application mApplication;
    public BaseApplicationUtils(Application application){
        this.mApplication = application;
    }
    public void initUmengSDK(String secret) {
        UMConfigure.init(mApplication, UMConfigure.DEVICE_TYPE_PHONE, secret);
        UMConfigure.setLogEnabled(true);
        // 选用MANUAL页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

    }
    public void initHttpClient(String baseUrl, String sourceCode){
        HttpClientUtil.getInstance().init(mApplication, baseUrl, sourceCode);
    }
    /**
     * 初始化检查更新配置
     */
    public void initUploadPlugin(String baseUrl, String urlName) {
        String url = baseUrl + File.separator + urlName;
        Map<String, String> map = new HashMap<>();
        UpdateConfig.getConfig()
                // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
                .setCheckEntity(new CheckEntity().setMethod("POST").setUrl(url).setParams(map))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .setUpdateParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) throws Exception {
                        LogUtils.e(response);
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                        ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                        if (resultBean == null || resultBean.getState() != 1) {
                            return null;
                        }

                        AppUpdateEntity updateEntity = JSONObject.parseObject(resultBean.getData()
                                , AppUpdateEntity.class);
                        Update update = new Update();
                        // 此apk包的下载地址
                        update.setUpdateUrl(updateEntity.getDownUrl());
                        // 此apk包的版本号
                        update.setVersionCode(updateEntity.getVersionNum());
                        // 此apk包的版本名称
                        update.setVersionName(updateEntity.getVersionName());
                        // 此apk包的更新内容
                        update.setUpdateContent(updateEntity.getUpdateInfo());
                        // 此apk包是否为强制更新
                        update.setForced(updateEntity.getIsForcedUpdate());
                        // 是否显示忽略此次版本更新按钮
                        update.setIgnore(true);
                        return update;
                    }
                })
                .setDownloadWorker(CustomDownloadWorker.class)
                .setFileChecker(new CustomFileChecker())
                .setFileCreator(new CustomFileCreator())
                .setUpdateStrategy(new UpdateStrategy() {
                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        // 是否在检查到有新版本更新时展示Dialog。
                        return true;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        // 是否自动更新.当为自动更新时。代表下载成功后不通知用户。直接调起安装。
                        return true;
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        // 在APK下载时。是否显示下载进度的Dialog
                        return true;
                    }
                });
    }
}
