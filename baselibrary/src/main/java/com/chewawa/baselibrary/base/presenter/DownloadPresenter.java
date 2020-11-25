package com.chewawa.baselibrary.base.presenter;

import com.chewawa.baselibrary.base.contract.DownloadContract;
import com.chewawa.baselibrary.base.model.DownloadModel;
import com.chewawa.baselibrary.utils.ToastUtils;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/19 12:03
 */
public class DownloadPresenter extends BasePresenterImpl<DownloadContract.View, DownloadModel> implements DownloadContract.Presenter, DownloadContract.OnDownloadFileListener, DownloadContract.OnSaveImageListener {
    public DownloadPresenter(DownloadContract.View view) {
        super(view);
    }

    @Override
    public DownloadModel initModel() {
        return new DownloadModel();
    }

    @Override
    public void downloadImage(String url, String savePath) {
        view.showProgressDialog();
        model.downloadImage(url, savePath, this);
    }

    @Override
    public void saveImage(String base64, String savePath) {
        view.showProgressDialog();
        model.saveImage(base64, savePath, this);
    }

    @Override
    public void onDownloadFileStart() {

    }

    @Override
    public void onDownloadProgress(int progress) {

    }

    @Override
    public void onDownloadFileComplete(String path) {
        view.hideProgressDialog();
        view.synchronizeToPhoto(path);
    }

    @Override
    public void onDownloadFileError(String error) {
        view.hideProgressDialog();
        ToastUtils.showToast(error);
    }

    @Override
    public void onSaveImageComplete(String path) {
        view.hideProgressDialog();
        view.synchronizeToPhoto(path);
    }
}
