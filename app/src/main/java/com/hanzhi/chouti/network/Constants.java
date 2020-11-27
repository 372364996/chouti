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
    /*获取教师详情*/
    public static final String GET_TEACHER_DETAIL_URL = "teacher/GetTeacherDetailsV2";
    /*获取课程时间列表*/
    public static final String GET_CLASS_TIME_URL = "teacher/GetAllTeacherTimeV2";
    /*获取课程时间Tab列表*/
    public static final String GET_CLASS_TIME_TAB_URL = "teacher/GetLast14DayDateV2";
    /*获取课程列表*/
    public static final String GET_CLASS_URL = "class/GetClassListV2";
    /*获取课程Tab列表*/
    public static final String GET_CLASS_TAB_URL = "class/GetClassListMenuV2";
    /*权益服务内容列表*/
    public static final String EQUITIES_SERVICE_CONTENT_LIST = "DaoShop/GetStoreProject";
    /*权益优惠力度列表*/
    public static final String EQUITIES_DISCOUNT_LIST = "DaoShop/GetStoreEquityDiscount";
    /*权益管理列表*/
    public static final String EQUITIES_LIST_URL = "DaoShop/EquityList";
    /*店铺基本信息修改*/
    public static final String STORE_BASIC_INFO_EDIT_URL = "DaoShop/EditBaseInfo";
    /*获取店铺服务列表*/
    public static final String STORE_SERVICE_URL = "DaoShop/GetStoreProject";
    /*店铺成员*/
    public static final String STORE_MEMBER_LIST_URL = "DaoShop/GetStoreStaff";
    /*添加店铺成员*/
    public static final String ADD_STORE_MEMBER_URL = "DaoShop/AddStoreStaff";
    /*编辑店铺成员信息*/
    public static final String EDIT_STORE_MEMBER_URL = "DaoShop/EditStoreStaff";
    /*删除店铺成员*/
    public static final String DELETE_STORE_MEMBER_URL = "DaoShop/DeleteStoreStaff";
    /*维修单*/
    public static final String REPAIR_BILL_URL = "DaoShopOrder/GetClaimOrder";
    /*新增店铺权益*/
    public static final String ADD_STORE_EQUITIES_URL = "DaoShop/AddStoreEquity";
    /*编辑店铺权益*/
    public static final String EDIT_STORE_EQUITIES_URL = "DaoShop/EditStoreEquity";
    /*下线店铺权益*/
    public static final String REMOVE_STORE_EQUITIES_URL = "DaoShop/StoreEquityOffline";
    /*引流列表筛选列表*/
    public static final String CUSTOMER_LIST_FILTER_DATA_URL = "DaoShop/FlowListData";
    /*引流列表*/
    public static final String CUSTOMER_LIST_URL = "DaoShop/FlowList";
    /*预约单*/
    public static final String RESERVATION_LIST_URL = "DaoShopOrder/GetMaintainOrder";
    /*获取预约保养单详情*/
    public static final String RESERVATION_LIST_DETAIL_URL = "DaoShopOrder/MaintainOrderDetails";
    /*确认接收保养预约单*/
    public static final String ACCEPT_RESERVATION_URL = "DaoShopOrder/ConfirmMaintainOrder";
    /*获取预约保养单出厂资料*/
    public static final String GET_RESERVATION_PHOTO_URL = "DaoShopOrder/GetMaintainFile";
    /*保存预约保养单出厂资料*/
    public static final String SAVE_RESERVATION_PHOTO_URL = "DaoShopOrder/MaintainUploadFile";
    /*结款记录*/
    public static final String MONEY_RECORD_URL = "DaoShopOrder/MaintainOrderPayRecord";
    /*设置页信息获取*/
    public static final String SETTING_PAGE_URL = "DaoShop/GetSetAboutInfo";
    /*获取店铺图库*/
    public static final String GET_STORE_PHOTO_URL = "DaoShop/GetStoreImgList";
    /*添加店铺图片*/
    public static final String ADD_STORE_PHOTO_URL = "DaoShop/AddStoreImg";
    /*删除店铺图片*/
    public static final String DELETE_STORE_PHOTO_URL = "DaoShop/DeleteStoreImg";
    /*营业执照OCR识别*/
    public static final String OCR_BUSINESS_LICENSE_URL = "DaoShopUser/BusinessLicense";
    /*编辑店铺合作信息*/
    public static final String EDIT_STORE_COOPERATIVE_INFO_URL = "DaoShop/EditCooperationInfo";
    /*编辑店铺营业信息*/
    public static final String EDIT_STORE_BUSINESS_INFO_URL = "DaoShop/EditBusinessInfo";
    /*获取所有门店列表*/
    public static final String GET_STORE_LIST_URL = "DaoShop/AllStoreList";



    /**
     * SharedPreferences的键值
     *
     * @author nanfeifei
     * @since 2016年7月21日上午11:07:12
     * @version 1.0
     */
    public final static class SpKey {
        public static final String KEY_IS_ONE_START = "IsOneStart";
        public static final String KEY_BASE_IP = "baseIp";
        public static final String KEY_SPLASH_INFO = "SplashInfo";
        public static final String RESULT_DATA_KEY = "data_";
        public static final String RESULT_VERSION_KEY = "v_";
        public static final String KEY_APP_CONTENTS = "AppContentsV2";
        public static final String KEY_UNREAD_MESSAGE_COUNT= "unreadMessageCount";
        public static final String KEY_PUSH_OPEN = "push_open_status";
        public static final String KEY_MOBILE = "mobile";
        public static final String KEY_TOKEN = "token";
        public static final String KEY_PRIVACY_POLICY = "isAgree";
        public static final String KEY_SHOW_BIND_WEICHAT = "isShowBindWeichat";
        public static final String KEY_DEVICE_TOKEN = "deviceToken";
        public static final String KEY_DOMAIN_NAME = "domain_name";
    }
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
