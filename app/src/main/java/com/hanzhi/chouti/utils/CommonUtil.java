package com.hanzhi.chouti.utils;

import com.chewawa.baselibrary.utils.BaseCommonUtil;
import com.chewawa.baselibrary.utils.SPUtils;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/18 18:43
 */
public class CommonUtil extends BaseCommonUtil {
    public static final String KEY_STORE_ID = "store_id";

    /**
     * 获取店铺id
     *
     * @return
     */
    public static int getStoreId() {
        return (int) SPUtils.get(KEY_STORE_ID, 0);
    }

    /**
     * 保存店铺id
     *
     * @return
     */
    public static void saveStoreId(int storeId) {
        SPUtils.put(KEY_STORE_ID, storeId);
    }
}
