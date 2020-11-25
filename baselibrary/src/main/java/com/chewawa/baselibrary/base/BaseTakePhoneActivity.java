package com.chewawa.baselibrary.base;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.utils.FileUtils;
import com.chewawa.baselibrary.utils.PermissionPageUtils;
import com.chewawa.baselibrary.utils.glide.GlideEngine;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/7/6 16:10
 */
public abstract class BaseTakePhoneActivity extends NBaseActivity implements DialogInterface.OnClickListener {
    protected QMUIDialog qmuiDialog;
    protected RxPermissions rxPermissions;
    protected PictureSelector pictureSelector;
    protected PictureSelectionModel pictureSelectionModel;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    public int initLoadResId() {
        return 0;
    }

    @Override
    protected void initView() {
        rxPermissions = new RxPermissions(this);


    }
    public void showTakePhoneDialog() {
        rxPermissions
                .requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> { // will emit 1 Permission object
                    if (permission.granted) {
                        // All permissions are granted !
                        if(qmuiDialog == null){
                            qmuiDialog = new QMUIDialog.MenuDialogBuilder(BaseTakePhoneActivity.this)
                                    .setSkinManager(QMUISkinManager.defaultInstance(BaseTakePhoneActivity.this))
                                    .addItems(getResources().getStringArray(R.array.take_phone_item), this)
                                    .create(mCurrentDialogStyle);
                        }
                        qmuiDialog.show();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        showTakePhoneDialog();
                    } else {
                        PermissionPageUtils.toPermissionSetting(BaseTakePhoneActivity.this);
                    }
                });
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case 0:{
                selectPhoto(true);
                break;
            }
            case 1:{
                selectPhoto(false);
                break;
            }
        }
        dialog.dismiss();
    }

    /**
     * 从相册选择图片
     * @param isCamera
     */
    public void selectPhoto(boolean isCamera){
        if(pictureSelector == null){
            pictureSelector = PictureSelector.create(BaseTakePhoneActivity.this);
        }
        pictureSelectionModel = null;
        if(isCamera){
            pictureSelectionModel = pictureSelector.openCamera(getPictureMimeType());
        }else {
            pictureSelectionModel = pictureSelector.openGallery(getPictureMimeType());

        }
        setPictureSelectionModel(pictureSelectionModel);
    }

    /**
     * 设置图片选择控件参数
     * @param pictureSelectionModel
     */
    public void setPictureSelectionModel(PictureSelectionModel pictureSelectionModel){
        pictureSelectionModel.setOutputCameraPath(FileUtils.getExternalCacheDir())// 自定义拍照保存路径
                .isCamera(false)
                .selectionMode(PictureConfig.SINGLE)
                .imageEngine(GlideEngine.createGlideEngine())
                .isPreviewImage(true)
                .isPreviewVideo(true)
                .isCompress(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 获取图库选择时资源类型
     * @return
     */
    public int getPictureMimeType(){
        return PictureMimeType.ofImage();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    List<String> resultList = new ArrayList<>();
                    for (int i = 0; i < selectList.size(); i++) {
                        LocalMedia media = selectList.get(i);
                        String path;
                        if (media.isCompressed()) {  //media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                            path = media.getCompressPath();//为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                        } else {  //media.getPath(); 为原图path
                            path = media.getPath();//为原图path
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            path = media.getAndroidQToPath();
                        }
                        resultList.add(path);
                    }
                    pictureSelectorResult(selectList, resultList);
            }
        }
    }

    /**
     * 图片选择结果获得
     * @param selectList
     * @param resultList
     */
    public abstract void pictureSelectorResult(List<LocalMedia> selectList, List<String> resultList);


}
