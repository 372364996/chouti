package com.chewawa.baselibrary.oss;

import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.chewawa.baselibrary.BaseApplication;
import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.oss.service.MyOSSAuthCredentialsProvider;
import com.chewawa.baselibrary.oss.service.OssService;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.just.agentweb.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2019/7/25 18:09
 */
public class OSSUtils implements OssService.OnGetFileNameListener, OssService.OnGetMultiFileNameListener {
    private static OSSUtils mOSSUtils = null;
    private OssService ossService;
    public static final String PRIVATE_BUCKET = "cyb-bucket-1";
    public static final String PUBLIC_BUCKET = "cyb-bucket-pr-1";
    public static final String PUBLIC_OSS_URL = "https://res2.cyb.yz.chewawa.com.cn/";
    OSS oss;

    private OSSUtils() {
    }

    public static OSSUtils getInstance() {
        if (mOSSUtils == null) {
            synchronized (OSSUtils.class) {
                if (mOSSUtils == null) {
                    mOSSUtils = new OSSUtils();
                }
            }
        }
        return mOSSUtils;
    }

    public OSS getOSSClient() {
        //使用自己的获取STSToken的类
        if (oss == null) {
            OSSCredentialProvider credentialProvider = new MyOSSAuthCredentialsProvider();
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            oss = new OSSClient(BaseApplication.getInstance(), "http://oss-cn-beijing.aliyuncs.com", credentialProvider, conf);
        }
        return oss;
    }

    public OssService getOssService() {
        if (ossService == null) {
            ossService = new OssService();
            ossService.initOss(getOSSClient());

        }
        return ossService;
    }

    /**
     * 上传公共资源（文件，图片等）
     *
     * @param filePath
     * @param onUploadImageListener
     */
    public void uploadPublicObject(String filePath, OssService.OnUploadImageListener onUploadImageListener) {
        getOssService().setBucketName(PUBLIC_BUCKET);
        getOssService().getFileName(filePath, onUploadImageListener, this);
    }

    /**
     * 上传公共资源（文件，图片等）
     *
     * @param filePathList
     * @param onUploadMultiImageListener
     */
    public void uploadPublicMultiObject(List<String> filePathList, OssService.OnUploadMultiImageListener onUploadMultiImageListener) {
        getOssService().setBucketName(PUBLIC_BUCKET);
        getOssService().getMultiFileName(filePathList, onUploadMultiImageListener, this);
    }

    /**
     * 上传公共资源（视频使用）
     *
     * @param filePath
     * @param onUploadImageListener
     */
    public void uploadPublicVideo(String filePath, OssService.OnUploadImageListener onUploadImageListener) {
        getOssService().setBucketName("cyb-bucket-mda");
        getOssService().getFileName(filePath, onUploadImageListener, this);
    }

    /**
     * 上传私有资源（文件，图片等）
     *
     * @param filePath
     * @param onUploadImageListener
     */
    public void uploadPrivateObject(String filePath, OssService.OnUploadImageListener onUploadImageListener) {
        getOssService().setBucketName(PRIVATE_BUCKET);
        getOssService().getFileName(filePath, onUploadImageListener, this);
    }

    /**
     * 上传公共资源（文件，图片等）
     *
     * @param filePathList
     * @param onUploadMultiImageListener
     */
    public void uploadPrivateMultiObject(List<String> filePathList, OssService.OnUploadMultiImageListener onUploadMultiImageListener) {
        getOssService().setBucketName(PRIVATE_BUCKET);
        getOssService().getMultiFileName(filePathList, onUploadMultiImageListener, this);
    }

    /**
     * 获取公共资源文件
     *
     * @param fileName
     * @param listener
     */
    public void getPublicObjectFile(String fileName, OssService.OnGetImageListener listener) {
        getOssService().setBucketName(PUBLIC_BUCKET);
        getOssService().asyncGetImage(fileName, listener);
    }

    /**
     * 获取公有视频链接
     *
     * @param fileName
     * @return
     */
    public String getPublicVideoURL(String fileName) {
//        return PUBLIC_OSS_URL+fileName;
        if (fileName.startsWith("http")) {
            return fileName;
        }
        return getOSSClient().presignPublicObjectURL("cyb-bucket-mda", fileName);
    }

    /**
     * 获取私有资源文件
     *
     * @param fileName
     * @param listener
     */
    public void getPrivateObjectFile(String fileName, OssService.OnGetImageListener listener) {
        getOssService().setBucketName(PRIVATE_BUCKET);
        getOssService().asyncGetImage(fileName, listener);
    }

    /**
     * 获取公有资源链接
     *
     * @param fileName
     * @return
     */
    public String getPublicObjectURL(String fileName) {
//        return PUBLIC_OSS_URL+fileName;
        if (fileName.startsWith("http")) {
            return fileName;
        }
        return getOSSClient().presignPublicObjectURL(PUBLIC_BUCKET, fileName);
    }

    /**
     * 获取私有资源链接
     *
     * @param fileName
     * @return
     */
    public void getPrivateObjectUrl(String fileName, final OnGetImageUrlListener listener) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        Observable.just(fileName)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String fileName) throws Exception {
                        String url = getOSSClient().presignConstrainedObjectURL(PRIVATE_BUCKET, fileName, 60 * 60);
                        return url;
                    }
                })
                .subscribeOn(Schedulers.newThread()) // s1
                .observeOn(AndroidSchedulers.mainThread()) // o2
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String url) throws Exception {
                        listener.onGetImageUrlSuccess(url);
                    }
                });
    }


    @Override
    public void onGetFileNameSuccess(OssService.OnUploadImageListener onUploadImageListener, String fileName, String filePath) {
        if (ossService == null) {
            LogUtils.e("OSS", "ossService为null");
            ToastUtils.showToast(R.string.activate_detail_upload_failure);
            return;
        }

        ossService.asyncPutImage(fileName, filePath, onUploadImageListener);
    }

    @Override
    public void onGetFileNameFailure(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void onGetMultiFileNameSuccess(final OssService.OnUploadMultiImageListener onUploadMultiImageListener, final List<String> fileNameList, List<String> filePathList) {
        if (ossService == null) {
            LogUtils.e("OSS", "ossService为null");
            ToastUtils.showToast(R.string.activate_detail_upload_failure);
            return;
        }
        final Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < fileNameList.size(); i++) {
            final int index = i;
            ossService.asyncPutImage(fileNameList.get(i), filePathList.get(i), new OssService.OnUploadImageListener() {
                @Override
                public void onUploadImageSuccess(String fileName) {
                    map.put(index, fileName);
                    if (map.size() == fileNameList.size()) {
                        onUploadMultiImageListener.onUploadMultiImageSuccess(fileNameList);
                    }
                }

                @Override
                public void onUploadImageFailure(String message) {
                    onUploadMultiImageListener.onUploadMultiImageFailure(message);
                }
            });
        }
    }

    @Override
    public void onGetMultiFileNameFailure(String message) {
        ToastUtils.showToast(message);
    }


    public interface OnGetImageUrlListener {
        void onGetImageUrlSuccess(String imageUrl);
    }
}
