package com.chewawa.baselibrary.oss.service;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.ImagePersistRequest;
import com.alibaba.sdk.android.oss.model.ImagePersistResult;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.chewawa.baselibrary.networkutils.BaseConstants;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.chewawa.baselibrary.oss.Config;
import com.umeng.umcrash.UMCrash;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mOss on 2015/12/7 0007.
 * 支持普通上传，普通下载
 */
public class OssService {

    public OSS mOss;
    private String mBucket;
    private String mCallbackAddress;
    private static String mResumableObjectKey = "resumableObject";
    public OssService(){
        init();
    }
    public OssService(OSS oss, String bucket) {
        this.mOss = oss;
        this.mBucket = bucket;
        init();
    }
    public void init(){
    }

    public void setBucketName(String bucket) {
        this.mBucket = bucket;
    }

    public void initOss(OSS _oss) {
        this.mOss = _oss;
    }

    public void setCallbackAddress(String callbackAddress) {
        this.mCallbackAddress = callbackAddress;
    }
    public void getFileName(final String filePath, final OnUploadImageListener onUploadImageListener, final OnGetFileNameListener listener){
        File file = new File(filePath);
        HttpManager.post(Config.STS_UPLOAD_URL+"?fileName="+file.getName())
                //如果有文件名字可以不用再传Type,会自动解析到是image/*
                .params("fileName", file.getName())
                .execute(new ApiCallBack() {
                    @Override
                    public void onError(int status, String message) {
                        listener.onGetFileNameFailure(message);
                    }

                    @Override
                    public void onSuccess(ResultBean resultBean) {
                        JSONObject jsonObject = JSONObject.parseObject(resultBean.getData());
                        String fileName = jsonObject.getString("FileName");
                        listener.onGetFileNameSuccess(onUploadImageListener, fileName, filePath);
                    }
                });
    }
    public void getMultiFileName(final List<String> filePathList, final OnUploadMultiImageListener onUploadMultiImageListener, final OnGetMultiFileNameListener listener){
        List<String> fileNameList = new ArrayList<>();
        for(int i =0; i<filePathList.size(); i++){
            File file = new File(filePathList.get(i));
            fileNameList.add(file.getName());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("fileNames", fileNameList);
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("Model", map);
        HttpManager.post(Config.STS_UPLOAD_URL)
                //如果有文件名字可以不用再传Type,会自动解析到是image/*
                .upJsonObject(postMap)
                .execute(new ApiCallBack() {
                    @Override
                    public void onError(int status, String message) {
                        listener.onGetMultiFileNameFailure(message);
                    }

                    @Override
                    public void onSuccess(ResultBean resultBean) {
                        JSONObject jsonObject = JSONObject.parseObject(resultBean.getData());
                        String fileNames = jsonObject.getString("FileNames");
                        List<String> fileNameList = JSONArray.parseArray(fileNames, String.class);
                        listener.onGetMultiFileNameSuccess(onUploadMultiImageListener, fileNameList, filePathList);
                    }
                });
    }
    public interface OnGetFileNameListener{
        void onGetFileNameSuccess(OnUploadImageListener onUploadImageListener, String fileName, String filePath);
        void onGetFileNameFailure(String message);
    }
    public interface OnGetMultiFileNameListener{
        void onGetMultiFileNameSuccess(OnUploadMultiImageListener onUploadMultiImageListener, List<String> fileNameList, List<String> filePathList);
        void onGetMultiFileNameFailure(String message);
    }
    public void asyncPutImage(final String object, String localFile, final OnUploadImageListener onUploadImageListener) {
        final long upload_start = System.currentTimeMillis();
        OSSLog.logDebug("upload start");

        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }

        // 构造上传请求
        OSSLog.logDebug("create PutObjectRequest ");
        PutObjectRequest put = new PutObjectRequest(mBucket, object, localFile);
        put.setCRC64(OSSRequest.CRC64Config.YES);
        if (mCallbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", mCallbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
            }
        });

        OSSLog.logDebug(" asyncPutObject ");
        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                onUploadImageListener.onUploadImageSuccess(object);
                Log.d("PutObject", "UploadSuccess");

                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

                long upload_end = System.currentTimeMillis();
                OSSLog.logDebug("upload cost: " + (upload_end - upload_start) / 1000f);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                    UMCrash.generateCustomLog(info, BaseConstants.NETWORK_ERROR);
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                    UMCrash.generateCustomLog(info, BaseConstants.SERVER_ERROR);
                }

                onUploadImageListener.onUploadImageFailure(info);

            }
        });
    }
    public interface OnUploadImageListener{
        void onUploadImageSuccess(String fileName);
        void onUploadImageFailure(String message);
    }
    public interface OnUploadMultiImageListener{
        void onUploadMultiImageSuccess(List<String> fileNameList);
        void onUploadMultiImageFailure(String message);
    }
    public void asyncGetImage(final String fileName, final OnGetImageListener listener) {
        final long get_start = System.currentTimeMillis();
        OSSLog.logDebug("get start");
        if ((fileName == null) || fileName.equals("")) {
            Log.w("AsyncGetImage", "ObjectNull");
            return;
        }
        if(listener == null){
            return;
        }

        OSSLog.logDebug("create GetObjectRequest");
        GetObjectRequest get = new GetObjectRequest(mBucket, fileName);
        get.setxOssProcess("image/resize,m_fixed,w_1080");
        get.setCRC64(OSSRequest.CRC64Config.YES);
        get.setProgressListener(new OSSProgressCallback<GetObjectRequest>() {
            @Override
            public void onProgress(GetObjectRequest request, long currentSize, long totalSize) {
                Log.d("GetObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
            }
        });
        OSSLog.logDebug("asyncGetObject");
        OSSAsyncTask task = mOss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();
                listener.onGetImageSuccess(fileName, inputStream);
                //Bitmap bm = BitmapFactory.decodeStream(inputStream);
//                try {
//                    //需要根据对应的View大小来自适应缩放
//                    Bitmap bm = mDisplayer.autoResizeFromStream(inputStream);
//                    long get_end = System.currentTimeMillis();
//                    OSSLog.logDebug("get cost: " + (get_end - get_start) / 1000f);
//                    mDisplayer.downloadComplete(bm);
//                    mDisplayer.displayInfo("Bucket: " + mBucket + "\nObject: " + request.getObjectKey() + "\nRequestId: " + result.getRequestId());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                    UMCrash.generateCustomLog(info, BaseConstants.NETWORK_ERROR);
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                    UMCrash.generateCustomLog(info, BaseConstants.SERVER_ERROR);
                }
                listener.onGetImageFailure(info);
            }
        });
    }
    public interface OnGetImageListener{
        void onGetImageSuccess(String fileName, InputStream inputStream);
        void onGetImageFailure(String message);
    }
    public void imagePersist(String fromBucket, String fromObjectKey, String toBucket, String toObjectkey, String action){

        ImagePersistRequest request = new ImagePersistRequest(fromBucket,fromObjectKey,toBucket,toObjectkey,action);

        OSSAsyncTask task = mOss.asyncImagePersist(request, new OSSCompletedCallback<ImagePersistRequest, ImagePersistResult>() {
            @Override
            public void onSuccess(ImagePersistRequest request, ImagePersistResult result) {
//                mDisplayer.displayInfo(result.getServerCallbackReturnBody());
            }

            @Override
            public void onFailure(ImagePersistRequest request, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                } else if (serviceException != null) {
                }
            }
        });
    }
}
