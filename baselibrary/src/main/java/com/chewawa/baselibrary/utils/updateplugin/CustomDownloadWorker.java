package com.chewawa.baselibrary.utils.updateplugin;

import com.chewawa.baselibrary.utils.FileUtils;
import com.just.agentweb.LogUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import org.lzh.framework.updatepluginlib.base.DownloadWorker;

import java.io.File;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/7/30 15:24
 */
public class CustomDownloadWorker extends DownloadWorker {
    @Override
    protected void download(String url, File target) throws Exception {
        EasyHttp.downLoad(url)
                .savePath(FileUtils.getAbsolutePath(target.getAbsolutePath()))
                .saveName(FileUtils.getFileName(target.getAbsolutePath()))
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        sendDownloadProgress(bytesRead, contentLength);
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(String path) {
                        LogUtils.i("file", path);
                        sendDownloadComplete(new File(path));
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.i("file", e.getMessage());
                        sendDownloadError(e);
                    }
                });
    }
}
