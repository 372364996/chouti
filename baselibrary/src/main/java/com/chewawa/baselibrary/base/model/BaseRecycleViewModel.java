package com.chewawa.baselibrary.base.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chewawa.baselibrary.base.bean.PageBean;
import com.chewawa.baselibrary.base.bean.PageResultBean;
import com.chewawa.baselibrary.base.contract.BaseRecycleViewContract;
import com.chewawa.baselibrary.networkutils.ApiUtils;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * 分页列表数据获取
 * nanfeifei
 * 2018/1/25 14:54
 *
 * @version 1.0
 */
public class BaseRecycleViewModel extends BaseModelImpl implements BaseRecycleViewContract.Model {
  @Override public void getListData(@ApiUtils.UrlType String urlType, String url, Map params, final Class type, int page
      , final BaseRecycleViewModel.OnListDataListener listener) {
      if(TextUtils.isEmpty(url)){
          return;
      }
    PageBean pageEntity = new PageBean();
    pageEntity.setPageIndex(page);
    Map<String, String> map = new HashMap<>();
    map.put("size", "20");
    map.put("index", String.valueOf(page));
    map.putAll(params);
      Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
          @Override
          public void onError(int status, String message) {
              listener.onListDataFailure(message);
          }

          @Override
          public void onSuccess(ResultBean resultBean) {
              listener.onListDataSuccess(getPageResultEntity(resultBean.getData(), type));
          }
      });
      disposableList.add(disposable);
  }


  public interface OnListDataListener<T> {
    void onListDataSuccess(PageResultBean<T> pageResultEntity);
    void onListDataFailure(String error);
  }
  public <T> PageResultBean<T> getPageResultEntity(String result, Class<T> type) {
      Object jsonObject = JSONObject.parse(result);
      if (jsonObject instanceof JSONObject) {//后台返回数据包含分页信息
//          if (((JSONObject) jsonObject).containsKey("DataList")) { }
          return JSON.parseObject(result,
                  new TypeReference<PageResultBean<T>>(type) {
                  });
      } else if (jsonObject instanceof JSONArray) { //后台返回数据不包含分页信息
          List<T> list = new ArrayList<>(JSONObject.parseArray(result, type));
          PageResultBean<T> pageResultEntity = new PageResultBean<>();
          pageResultEntity.setDataList(list);
          return pageResultEntity;
      }

      return null;
  }
}
