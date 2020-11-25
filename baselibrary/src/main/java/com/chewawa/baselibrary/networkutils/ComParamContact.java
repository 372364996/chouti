package com.chewawa.baselibrary.networkutils;

public class ComParamContact {
    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe"
            + "\r" + "7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra" + "\r"
            + "z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG" + "\r"
            + "9aYqgE7zyTRZYX9byQIDAQAB" + "\r";
    public final static class Common {
        public final static String APPID = "appId";
        public final static String ACCESSTOKEN = "token";
        public final static String TIMESTAMP = "timestamp";
        public final static String REFRESH_TOKEN = "refreshToken";
        public final static String SIGN = "sign";
    }

    public final static class Code {
        //http请求成功状态码
        public final static int HTTP_SUCESS = 0;
        //AccessToken错误或已过期
        public static final int ACCESS_TOKEN_EXPIRED = 100010101;
        //RefreshToken错误或已过期
        public static final int REFRESH_TOKEN_EXPIRED = 100010102;
        //帐号在其它手机已登录
        public static final int OTHER_PHONE_LOGINED = 100021006;
        //timestamp过期
        public static final int TIMESTAMP_ERROR = 100010104;
        //缺少授权信息,没有accessToken,应该是没有登录
        public final static int NO_ACCESS_TOKEN = 100010100;
        //签名错误
        public final static int ERROR_SIGN = 100010105;
        //设备未绑定
        public final static int DEVICE_NO_BIND = 100022001;
    }


}
