 /*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chewawa.baselibrary.networkutils.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.BaseApplication;
import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.networkutils.BaseConstants;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.chewawa.baselibrary.utils.LogUtils;
import com.umeng.umcrash.UMCrash;
import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.PostRequest;

import java.io.File;

import io.reactivex.disposables.Disposable;

/**
 * <p>描述：扩展PostRequest请求，解决自定义ApiResult重复写代理的问题</p>
 * 1.用test2包中实例举例,假如你的自定义ApiResult是TestApiResult2<br>
 * 作者： zhouyou<br>
 * 日期： 2017/7/7 10:23 <br>
 * 版本： v1.0<br>
 */
public class CustomPostRequest extends PostRequest {
    public CustomPostRequest(String url) {
        super(url);
    }

    public CustomPostRequest upJsonObject(Object objBean) {//若果你的是obj,你想很容易转json,可以自己新增一个方法obj转json的
        LogUtils.i("jsonObject", JSON.toJSONString(objBean));
        super.upJson(JSON.toJSONString(objBean));
        return this;
    }
    public CustomPostRequest upJsonStr(String json) {
        super.upJson(json);
        return this;
    }
    public CustomPostRequest params(String key, String value){
        super.params(key, value);
        return this;
    }
    public CustomPostRequest params(String key, File file, String fileName, ProgressResponseCallBack responseCallBack){
        super.params(key, file, fileName, responseCallBack);
        return this;
    }

    @Override
    public CustomPostRequest syncRequest(boolean syncRequest) {
        super.syncRequest(syncRequest);
        return this;
    }

    public Disposable execute(final ApiCallBack callBack) {
        return execute(new CallBack<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(ApiException e) {
                String error = BaseApplication.getInstance().getString(R.string.no_network_toast);
                callBack.onError(e.getCode(), error);
            }

            @Override
            public void onSuccess(String result) {
                ResultBean resultBean = JSONObject.parseObject(result, ResultBean.class);
                if(resultBean!=null){
                    if(resultBean.isSuccess()){
                        callBack.onSuccess(resultBean);
                    }else {
                        callBack.onError(resultBean.getState(), resultBean.getMsg());
                    }
                }

            }
        });
    }
}
