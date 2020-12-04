package com.hanzhi.chouti.network;

import com.chewawa.baselibrary.networkutils.BaseConstants;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2019/7/11 16:25
 */
public class Constants extends BaseConstants {
    /*是否是开发模式*/
    public final static String SERVICE_TEL = "400-722-788";
    public final static String SOURCE_TYPE = "89ddd026-d507-441c-aed8-94d2dbace01e"; //安卓
    public final static String WEICHAT_APP_ID = "wx91a009977d8d6c60";

    //测试服务器地址
    public static final String BASE_NATIVE_IP = "http://192.168.9.232:8001";
    //中间版本服务器地址
    public static final String BASE_PREVIEW_IP = "https://api.cyb.yz.chewawa.com.cn/shunshoutui";
    // 服务器地址
    public static final String BASE_IP = "http://hanzhiapp.hdlebaobao.cn";
    public static final String IMAGE_IP = BASE_IP;

    /*全局公共参数统一配置接口*/
    public static final String GET_APP_GLOBAL_SETTING_URL = "AppGlobalSetting/GetAppGlobalSet";
    /*检查更新*/
    public static final String UPLOAD_APP_URL = "api/AppGlobalSetting/GetDaoShopVersionInfoes";
    /*登录获取验证码*/
    public static final String LOGIN_AUTH_CODE_URL = "DaoShopUser/SendCode";
    /*密码登录*/
    public static final String PASSWORD_LOGIN_URL = "DaoShopUser/Login";
    /*验证码登录*/
    public static final String AUTH_CODE_LOGIN_URL = "DaoShopUser/Login";
    /*注册*/
    public static final String REGISTER_URL = "DaoShopUser/Register";
    /*获取店铺类型*/
    public static final String STORE_TYPE_URL = "DaoShop/GetStoreType";
    /*获取教师列表*/
    public static final String GET_TEACHER_LIST_URL = "teacher/GetTeacherListV2";
    /*获取评价列表*/
    public static final String GET_APPRAISE_LIST_URL = "order/GetOrderOfScoreByTeacherIdV2";
    /*获取教师详情*/
    public static final String GET_TEACHER_DETAIL_URL = "teacher/GetTeacherDetailsV2";
    /*收藏教师*/
    public static final String COLLECT_TEACHER_APPLY = "teacher/AddEvaluationV2";
    /*获取课程时间列表*/
    public static final String GET_CLASS_TIME_URL = "teacher/GetAllTeacherTimeV2";
    /*获取课程时间Tab列表*/
    public static final String GET_CLASS_TIME_TAB_URL = "teacher/GetLast14DayDateV2";
    /*获取课程列表*/
    public static final String GET_CLASS_URL = "class/GetClassListV2";
    /*获取课程Tab列表*/
    public static final String GET_CLASS_TAB_URL = "class/GetClassListMenuV2";
    /*获取课程详情*/
    public static final String GET_CLASS_DETAIL_LIST = "class/GetClassMaterialsV2";
    /*获取我的课程Tab列表*/
    public static final String GET_MY_CLASS_TAB_URL = "order/GetMyOrderListMenuV2";
    /*获取我的课程列表*/
    public static final String GET_MY_CLASS_URL = "order/GetMyOrderListByUserIdV2";
    /*获取我的钱包列表*/
    public static final String GET_MY_WALLET_URL = "classcard/MyClassCard";
    /*获取我的老师列表*/
    public static final String GET_MY_TEACHER_URL = "teacher/GetMyTeachersByUserIdV2";
    /*获取我的课程详情*/
    public static final String GET_MY_CLASS_DETAIL_URL = "order/GetClassOrderRemainingTimeV2";
    /*提交课程评价*/
    public static final String SUBMIT_CLASS_APPRAISE_URL = "order/ScoreOfOrderV2";
    /*获取课程确认提示信息*/
    public static final String GET_CLASS_APPLY_TIPS = "classcard/GetApplyClassNotesV2";
    /*获取用户课程卡列表*/
    public static final String GET_CLASS_CARD_LIST = "classcard/MyClassCardV2";
    /*提交课程申请*/
    public static final String SUBMIT_CLASS_APPLY = "class/CreateOrderV2";
    /*重新申请课程*/
    public static final String REAPPLY_CLASS_APPLY = "class/CreateOrderV2";
    /*取消课程*/
    public static final String CANCEL_CLASS_APPLY = "order/UserCancelOrderV2";
    /*是否可以进入课堂*/
    public static final String JOIN_CLASS_APPLY = "order/IsCanEnterV2";
    /*获取我们的联系方式（官方联系方式）*/
    public static final String GET_US_INFO_APPLY = "teacher/ContactInfoV2";

    /**
     * Bundle的key
     */
    public final static class BundleKey {
        public static final String WEB_URL = "url";
        public static final String FLAG = "flag";
        public static final String FROM = "from";
        public static final String TITLE = "title";
    }
    /**
     * 异常类型
     */
    public static final String SERVER_ERROR = "serverError";
    public static final String NETWORK_ERROR = "networkError";
}
