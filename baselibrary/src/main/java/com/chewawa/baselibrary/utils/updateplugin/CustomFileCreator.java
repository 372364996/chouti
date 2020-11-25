package com.chewawa.baselibrary.utils.updateplugin;

import android.content.Context;

import org.lzh.framework.updatepluginlib.base.FileCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;

import java.io.File;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/7/31 13:45
 */
public class CustomFileCreator extends FileCreator {
    @Override
    public File create(Update update) {
        File cacheDir = getCacheDir();
        cacheDir.mkdirs();
        return new File(cacheDir,"update_normal_" + update.getVersionName()+".apk");
    }

    @Override
    public File createForDaemon(Update update) {
        File cacheDir = getCacheDir();
        cacheDir.mkdirs();
        return new File(cacheDir,"update_daemon_" + update.getVersionName()+".apk");
    }

    private File getCacheDir() {
        Context context = ActivityManager.get().getApplicationContext();
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        cacheDir = new File(cacheDir,"update");
        return cacheDir;
    }
}
