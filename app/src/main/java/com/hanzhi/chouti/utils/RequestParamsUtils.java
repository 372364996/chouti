package com.hanzhi.chouti.utils;

import android.text.TextUtils;

import com.hanzhi.chouti.bean.ClassApplyBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/27 21:16
 */
public class RequestParamsUtils {
    public static Map<String, Object> getClassApplyParams(ClassApplyBean classApplyBean){
        Map<String, Object> map = new HashMap<>();
        if(classApplyBean != null){
            if(classApplyBean.getClassId() != 0){
                map.put("classId", classApplyBean.getClassId());
            }
            if(classApplyBean.getTeacherId() != 0){
                map.put("teacherId", classApplyBean.getTeacherId());
            }
            if(!TextUtils.isEmpty(classApplyBean.getDateTimeStr())){
                map.put("dateTimeStr", classApplyBean.getDateTimeStr());
            }
        }
        return map;

    }
}
