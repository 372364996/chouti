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

package com.hanzhi.onlineclassroom.network.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.baileren.rsalibrary.AESCipherStrategy;
import com.baileren.rsalibrary.RSACipherStrategy;
import com.chewawa.baselibrary.networkutils.ComParamContact;
import com.chewawa.baselibrary.networkutils.interceptor.BaseCustomDynamicInterceptor;
import com.chewawa.baselibrary.utils.BaseCommonUtil;
import com.hanzhi.onlineclassroom.utils.CommonUtil;

import java.util.TreeMap;


/**
 * <p>描述：对参数进行签名、添加token、时间戳处理的拦截器</p>
 * 主要功能说明：<br>
 * 因为参数签名没办法统一，签名的规则不一样，签名加密的方式也不同有MD5、BASE64等等，只提供自己能够扩展的能力。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 15:21 <br>
 * 版本： v1.0<br>
 */
public class CustomSignInterceptor extends BaseCustomDynamicInterceptor<CustomSignInterceptor> {
    private String sourceCode;
    public CustomSignInterceptor(String sourceCode){
        this.sourceCode = sourceCode;
    }
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            dynamicMap.put(ComParamContact.Common.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        }
        dynamicMap.put(ComParamContact.Common.ACCESSTOKEN, CommonUtil.getToken());
        dynamicMap.put("userId", CommonUtil.getToken());
//        if (isAccessToken()) {//是否添加token
//            String acccess = TokenManager.getInstance().getAuthModel().getAccessToken();
//            dynamicMap.put(ComParamContact.Common.ACCESSTOKEN, acccess);
//        }
//        if (isSign()) {//是否签名,因为你的字段key可能不是sign，这种动态的自己处理
//            dynamicMap.put(ComParamContact.Common.SIGN, sign(dynamicMap));
//        }
        //HttpLog.i("dynamicMap:" + dynamicMap.toString());
        return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }

    @Override
    public String dynamicJson(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        jsonObject.put("SourceCode", sourceCode);
        jsonObject.put("DeviceToken", BaseCommonUtil.getDeviceToken());
        jsonObject.put("userId", CommonUtil.getToken());
        return jsonObject.toJSONString();
//        return requestStr;
    }

    //    //签名规则：POST+url+参数的拼装+secret
    private String encrypt(String requestJson) {
        String signStr;
        String secretKey = AESCipherStrategy.generateKeyString();
        signStr = AESCipherStrategy.encrypt(requestJson, secretKey);
        RSACipherStrategy rsaCipherStrategy = new RSACipherStrategy();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ListStrEnc", signStr);  //密文
        jsonObject.put("EncKey", rsaCipherStrategy.encrypt(ComParamContact.RSA_PUBLIC_KEY, secretKey));  //秘钥
        jsonObject.put("IsEncryp", "1");  //是否加密
        return jsonObject.toJSONString();
    }
}
