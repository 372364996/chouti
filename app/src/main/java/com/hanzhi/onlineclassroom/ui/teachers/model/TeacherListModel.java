package com.hanzhi.onlineclassroom.ui.teachers.model;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.teachers.contract.TeacherListContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/3 16:20
 */
public class TeacherListModel extends BaseModelImpl implements TeacherListContract.Model {
    @Override
    public void collectTeacher(int teacherId, boolean isFans, TeacherListContract.OnCollectTeacherListener listener) {
        String url = Constants.COLLECT_TEACHER_APPLY;
        Map<String, String> map = new HashMap<>();
        map.put("teacherId", String.valueOf(teacherId));
        map.put("isFans", String.valueOf(isFans));
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onCollectTeacherFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                Boolean isFans = JSONObject.parseObject(resultBean.getData(), Boolean.class);
                listener.onCollectTeacherSuccess(isFans);
            }
        });
        disposableList.add(disposable);
    }
}
