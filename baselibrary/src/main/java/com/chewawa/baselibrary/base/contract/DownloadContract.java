package com.chewawa.baselibrary.base.contract;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/19 12:03
 */
public interface DownloadContract {
    interface Model {
        void downloadImage(String url, String savePath, OnDownloadFileListener listener);
        void saveImage(String base64, String savePath, OnSaveImageListener listener);
    }
    interface OnDownloadFileListener{
        void onDownloadFileStart();
        void onDownloadProgress(int progress);
        void onDownloadFileComplete(String path);
        void onDownloadFileError(String error);
    }
    interface OnSaveImageListener{
        void onSaveImageComplete(String path);
    }
    interface View extends BaseContract.View{
        void synchronizeToPhoto(String path);
    }

    interface Presenter {
        void downloadImage(String url, String savePath);
        void saveImage(String base64, String savePath);
    }
}
