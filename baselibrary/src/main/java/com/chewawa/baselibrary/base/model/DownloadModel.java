package com.chewawa.baselibrary.base.model;

import android.graphics.Bitmap;
import android.os.Environment;

import com.chewawa.baselibrary.base.contract.DownloadContract;
import com.chewawa.baselibrary.utils.FileUtils;
import com.chewawa.baselibrary.utils.LogUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/19 12:03
 */
public class DownloadModel extends BaseModelImpl implements DownloadContract.Model {
    @Override
    public void downloadImage(String url, String savePath, DownloadContract.OnDownloadFileListener listener) {
        EasyHttp.downLoad(url)
                .savePath(savePath)
                .saveName(FileUtils.getFileName(url))
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        listener.onDownloadProgress(progress);
                    }

                    @Override
                    public void onStart() {
                        listener.onDownloadFileStart();
                    }

                    @Override
                    public void onComplete(String path) {
                        LogUtils.i("file", path);
                        listener.onDownloadFileComplete(path);
                    }

                    @Override
                    public void onError(ApiException e) {
                        listener.onDownloadFileError(e.getMessage());
                    }
                });
    }

    @Override
    public void saveImage(String base64, String savePath, DownloadContract.OnSaveImageListener listener) {
        Observable.just(base64)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String base64) throws Exception {
                        String base64Str = base64.substring(base64.indexOf(",") + 1);
                        Bitmap bitmap = FileUtils.Base64ToBitMap(base64Str);
                        String saveFullPath = savePath
                                + File.separator+System.currentTimeMillis()+".jpg";
                        FileUtils.saveBitmap(bitmap, saveFullPath);
                        return saveFullPath;
                    }
                })
                .subscribeOn(Schedulers.newThread()) // s1
                .observeOn(AndroidSchedulers.mainThread()) // o2
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String savePath) throws Exception {
                        listener.onSaveImageComplete(savePath);
                    }
                });
    }
}
