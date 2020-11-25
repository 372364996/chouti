package com.chewawa.baselibrary.oss.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.utils.DateUtil;
import com.baileren.rsalibrary.AESCipherStrategy;
import com.baileren.rsalibrary.RSACipherStrategy;
import com.chewawa.baselibrary.networkutils.ComParamContact;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.chewawa.baselibrary.oss.Config;
import com.chewawa.baselibrary.oss.bean.OSSBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2019/7/26 13:33
 */
public class MyOSSAuthCredentialsProvider extends OSSFederationCredentialProvider {
    private OSSBean ossBean;
    RSACipherStrategy rsaCipherStrategy;

    public MyOSSAuthCredentialsProvider() {
        rsaCipherStrategy = new RSACipherStrategy();
    }

    @Override
    public OSSFederationToken getFederationToken() throws ClientException {
        String url = Config.STS_SERVER_URL;
        String secretKey = AESCipherStrategy.generateKeyString();
        String signStr = rsaCipherStrategy.encrypt(ComParamContact.RSA_PUBLIC_KEY, secretKey);
        String encoderStr = null;
        try {
            encoderStr = URLEncoder.encode(signStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encoderStr = signStr.replace("+", "%2B");
        } finally {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("EncKey", encoderStr);
            JSONObject modelObject = new JSONObject();
            modelObject.put("Model", jsonObject);
            Disposable disposable = HttpManager.post(url).upJsonObject(modelObject).syncRequest(true).execute(new ApiCallBack() {
                @Override
                public void onError(int status, String message) {
                    try {
                        throw new ClientException("ErrorCode: " + status + "| ErrorMessage: " + message);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(ResultBean resultBean) {
                    String data = AESCipherStrategy.decrypt(resultBean.getData(), secretKey);
                    ossBean = JSONObject.parseObject(data, OSSBean.class);
                }
            });
        }
        OSSFederationToken authToken = new OSSFederationToken(ossBean.getAccessKeyId()
                , ossBean.getAccessKeySecret(), ossBean.getSecurityToken(), setExpirationInGMTFormat(ossBean.getExpiration()));
        return authToken;
    }
    public long setExpirationInGMTFormat(String expirationInGMTFormat) {
        long expiration;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = sdf.parse(expirationInGMTFormat);
            expiration = date.getTime() / 1000;
        } catch (ParseException e) {
            if (OSSLog.isEnableLog()) {
                e.printStackTrace();
            }
            expiration = DateUtil.getFixedSkewedTimeMillis() / 1000 + 30;
        }
        return expiration;
    }
}
