package com.chewawa.baselibrary.base.contract;


import com.chewawa.baselibrary.base.model.BaseRecycleViewModel;
import com.chewawa.baselibrary.networkutils.ApiUtils;

import java.util.List;
import java.util.Map;

/**
 * ${tags}
 * nanfeifei
 * 2018/1/25 14:54
 *
 * @version 1.0
 */
public interface BaseRecycleViewContract {
  interface Model<T> {
    void getListData(@ApiUtils.UrlType String urlType, String url, Map<String, String> params, Class<T> type, int page,
                     BaseRecycleViewModel.OnListDataListener listener);
  }

  interface View extends BaseContract.View{
    void setListData(boolean isRefresh, List list, boolean isShowMore);
    void setTotalCount(int totalCount);
    void showLoadFail(boolean hasNetWork);
  }

  interface Presenter<T> {
    void getListData(@ApiUtils.UrlType String urlType, String url, Map<String, String> params, Class<T> type, boolean isRefresh);
  }
}
