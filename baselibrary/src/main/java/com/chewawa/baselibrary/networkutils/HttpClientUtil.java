package com.chewawa.baselibrary.networkutils;


import android.app.Application;
import android.text.TextUtils;

import com.chewawa.baselibrary.networkutils.interceptor.CommonSignInterceptor;
import com.chewawa.baselibrary.utils.BaseCommonUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.utils.HttpLog;
import com.zhouyou.http.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;


/**
 * 请求配置初始化
 * nanfeifei
 * 2017/6/15 11:13
 *
 * @version 3.7.0
 */
public class HttpClientUtil {
    final List<Interceptor> interceptors = new ArrayList<>();
    private volatile static HttpClientUtil singleton = null;

    public static HttpClientUtil getInstance() {
        if (singleton == null) {
            synchronized (HttpClientUtil.class) {
                if (singleton == null) {
                    singleton = new HttpClientUtil();
                }
            }
        }
        return singleton;
    }

    public HttpClientUtil addInterceptor(Interceptor interceptor) {
        interceptors.add(Utils.checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    public void init(Application application, String baseUrl, String sourceCode) {
        EasyHttp.init(application);
        String url = baseUrl + ApiUtils.DEFAULT;
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put("version", BaseCommonUtil.getVersion());
        headers.put("SourceCode", sourceCode);
        //设置请求参数
        HttpParams params = new HttpParams();
        params.put("SourceCode", sourceCode);
        params.put("DeviceToken", BaseCommonUtil.getDeviceToken());
        EasyHttp easyHttp = EasyHttp.getInstance()
                .debug("RxEasyHttp", true)
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setRetryCount(0)//默认网络不好自动重试1次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(url)
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setCacheVersion(1)//缓存版本为1
//                .setHostnameVerifier(new UnSafeHostnameVerifier(url))//全局访问规则
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                .addCommonHeaders(headers);//设置全局公共头
//                .addCommonParams(params)//设置全局公共参数
        easyHttp.setCertificates();  //信任所有证书
        if (interceptors != null && interceptors.size() > 0) {
            for (int i = 0; i < interceptors.size(); i++) {
                easyHttp.addInterceptor(interceptors.get(i));
            }
        } else {
            easyHttp.addInterceptor(new CommonSignInterceptor(sourceCode));//添加参数签名拦截器
        }
        //.addInterceptor(new HeTInterceptor());//处理自己业务的拦截器
    }

    public void resetBaseUrl(String baseUrl) {
        if (!TextUtils.isEmpty(baseUrl)) {
            EasyHttp.getInstance().setBaseUrl(baseUrl);
        }

    }

    public class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            HttpLog.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            HttpLog.i("############### verify " + hostname + " " + this.host);
            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
                return false;
            return true;
        }
    }
}
