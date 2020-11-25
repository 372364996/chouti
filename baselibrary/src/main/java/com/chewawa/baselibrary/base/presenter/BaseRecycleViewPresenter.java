package com.chewawa.baselibrary.base.presenter;




import com.chewawa.baselibrary.BaseApplication;
import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.base.bean.PageResultBean;
import com.chewawa.baselibrary.base.contract.BaseRecycleViewContract;
import com.chewawa.baselibrary.base.model.BaseRecycleViewModel;
import com.chewawa.baselibrary.networkutils.ApiUtils;
import com.chewawa.baselibrary.utils.ToastUtils;

import java.util.Map;

/**
 *
 * 分页列表逻辑处理
 * 2018/1/25 14:54
 *
 * @version 1.0
 */
public class BaseRecycleViewPresenter extends BasePresenterImpl<BaseRecycleViewContract.View, BaseRecycleViewModel>
        implements BaseRecycleViewContract.Presenter,
    BaseRecycleViewModel.OnListDataListener {
  int page;
  boolean hasMore;
  boolean isRefresh;
  public BaseRecycleViewPresenter(BaseRecycleViewContract.View view){
    super(view);
  }

  @Override
  public BaseRecycleViewModel initModel() {
    return new BaseRecycleViewModel();
  }

  @Override public void getListData(@ApiUtils.UrlType String urlType, String url, Map params, Class type, boolean isRefresh) {
    this.isRefresh = isRefresh;
//    if(TextUtils.isEmpty(url)){
//      return;
//    }
      if(isRefresh){
        page = 1;
        view.showProgressDialog();
      }
      model.getListData(urlType, url, params, type, page, this);
  }

  @Override public void onListDataSuccess(PageResultBean pageResultEntity) {
      view.hideProgressDialog();
    if(pageResultEntity == null||pageResultEntity.getDataList() == null|| pageResultEntity
        .getDataList().isEmpty()){
      if(isRefresh){
        view.setTotalCount(0);
        view.showLoadFail(true);
      }else {
        view.setListData(isRefresh , null, false);
      }
      return;
    }
    if(pageResultEntity.getPageTotalNumber()>0){ //代表后台有返回总页数
      if(pageResultEntity.getPageTotalNumber()>page){
        hasMore = true;
      }else {
        hasMore = false;
      }
    }else {  //后台无返回总页数或者未返回页数信息
      hasMore = true;
    }

    view.setTotalCount(pageResultEntity.getTotalCount());
    view.setListData(isRefresh, pageResultEntity.getDataList(), hasMore);
    page++;
  }

  @Override public void onListDataFailure(String error) {
      view.hideProgressDialog();
    if(isRefresh){
      if(BaseApplication.getInstance().getString(R.string.no_network_toast).equals(error)){//遗留问题，用来区分是网络异常还是请求异常，添加state字段改动太大
        view.showLoadFail(false);
      }else {
        ToastUtils.showToast(error);
        view.showLoadFail(true);
      }
    }else {
      view.setListData(isRefresh , null, true);
    }

  }


}
