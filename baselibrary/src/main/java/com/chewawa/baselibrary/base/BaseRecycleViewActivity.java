package com.chewawa.baselibrary.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.R2;
import com.chewawa.baselibrary.base.contract.BaseRecycleViewContract;
import com.chewawa.baselibrary.base.decoration.SpaceItemDecoration;
import com.chewawa.baselibrary.base.presenter.BaseRecycleViewPresenter;
import com.chewawa.baselibrary.networkutils.ApiUtils;
import com.chewawa.baselibrary.utils.DensityUtil;
import com.chewawa.baselibrary.view.RecyclerViewNoBugLinearLayoutManager;
import com.chewawa.baselibrary.view.VerticalSwipeRefreshLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * BaseRecycleViewActivity（支持分页，添加Header，footer，无数据，无网络布局）
 * nanfeifei
 * 2018/1/25 14:46
 *
 * @version 1.0
 */
public abstract class BaseRecycleViewActivity<T> extends NBaseActivity<BaseRecycleViewPresenter> implements BaseRecycleViewContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemLongClickListener {
    @BindView(R2.id.rv_list)
    protected RecyclerView rvList;
    @Nullable
    @BindView(R2.id.swipe_refresh)
    public VerticalSwipeRefreshLayout swipeRefresh;

    public Map<String, Object> params;
    protected View headerView;
    protected View footerView;
    private View notDataView;
    private View errorView;
    private ImageView ivNoData;
    private TextView tvNoData;
    private QMUIRoundButton btnInvite;
    protected int height;//item间距，单位dp
    protected BaseRecycleViewAdapter baseRecycleViewAdapter;
    protected boolean isRefresh;
    protected boolean hasData;
    protected boolean isEnableLoadMore = true; //默认启用分页加载

    @Override
    protected void initView() {
        initRecyclerView();
        isHideEmptyView();
        setNotDataView(); //setNotDataView();
        setErrorView();
        showProgressDialog();
    }

    @Override
    protected void initData() {
        super.initData();
        isRefresh = true;
        params = new HashMap<>();
    }

    @Override
    public BaseRecycleViewPresenter initPresenter() {
        return new BaseRecycleViewPresenter(this);
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        getListData();
    }

    /**
     * 请求列表数据
     */
    protected void getListData() {
        if (getParams() != null) {
            params = getParams();
        }
        presenter.getListData(getUrlType(), getUrl(), params, getResultClass(), isRefresh);
    }

    protected void initRecyclerView() {
        initRecyclerView(new SpaceItemDecoration(this, 0, getDividerHeight()));
    }

    protected void initRecyclerView(@NonNull RecyclerView.ItemDecoration decor) {
        initRecyclerView(getLayoutManager(), decor);
    }

    protected void initRecyclerView(@NonNull RecyclerView.LayoutManager layout, @NonNull RecyclerView.ItemDecoration decor) {
        setSwipeRefreshStyle();
        rvList.setLayoutManager(layout);
        rvList.setHasFixedSize(true);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(decor);
        setAdapter();
    }

    /**
     * 设置下拉刷新控件样式
     */
    protected void setSwipeRefreshStyle() {
        if (swipeRefresh != null) {
            swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(this, 48));
            swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                    android.R.color.holo_red_light, android.R.color.holo_orange_light,
                    android.R.color.holo_green_light);
            setOnRefreshListener();
        }

    }

    /**
     * 设置Adapter
     */
    protected void setAdapter() {
        baseRecycleViewAdapter = getAdapter();
        if (itemLoadAnimation() != null) {
            baseRecycleViewAdapter.openLoadAnimation(itemLoadAnimation());
        }
        baseRecycleViewAdapter.setOnItemClickListener(this);
        baseRecycleViewAdapter.setOnItemChildClickListener(this);
        baseRecycleViewAdapter.setOnItemLongClickListener(this);
        rvList.setAdapter(baseRecycleViewAdapter);
        headerView = addHeaderView();
        if (headerView != null) {
            baseRecycleViewAdapter.addHeaderView(headerView);
        }
        footerView = addFooterView();
        if (footerView != null) {
            baseRecycleViewAdapter.addFooterView(footerView);
        }
        baseRecycleViewAdapter.setOnLoadMoreListener(this, rvList);
    }

    /**
     * 设置自定义动画
     */
    public BaseAnimation itemLoadAnimation() {
        return null;
    }

    /**
     * 设置Header与Footer是否可以与空页面并存
     *
     * @param isHeadAndEmpty
     * @param isFootAndEmpty
     */
    protected void setHeaderFooterEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        if (baseRecycleViewAdapter != null) {
            baseRecycleViewAdapter.setHeaderFooterEmpty(isHeadAndEmpty, isFootAndEmpty);
        }
    }

    /**
     * 设置Header是否可以与空页面并存
     *
     * @param isHeadAndEmpty
     */
    protected void setHeaderAndEmpty(boolean isHeadAndEmpty) {
        if (baseRecycleViewAdapter != null) {
            baseRecycleViewAdapter.setHeaderAndEmpty(isHeadAndEmpty);
        }
    }

    /**
     * 是否显示数据为空时的空View，默认显示
     *
     * @return
     */
    protected boolean isHideEmptyView() {
        return false;
    }

    /**
     * 设置下拉刷新监听
     */
    protected void setOnRefreshListener() {
        if (swipeRefresh != null)
            swipeRefresh.setOnRefreshListener(this);
    }

    /**
     * 设置下拉刷新控件刷新状态
     *
     * @param refreshing
     */
    protected void setRefreshing(boolean refreshing) {
        if (swipeRefresh != null)
            swipeRefresh.setRefreshing(refreshing);
    }

    /**
     * 是否启用分页加载
     *
     * @param isEnable
     */
    protected void setEnableLoadMore(boolean isEnable) {
        isEnableLoadMore = isEnable;
    }

    /**
     * 恢复下拉刷新配置
     */
    protected void refreshWithSwipe() {
        baseRecycleViewAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        isRefresh = true;
        hasData = false;
    }

    /**
     * 加载完成之后恢复所有状态
     */
    protected void resetView() {
        baseRecycleViewAdapter.setEnableLoadMore(isEnableLoadMore);
        setRefreshing(false);
        isRefresh = false;
        hideProgressDialog();
        if (!hasData) {  //防止加载更多时也请求
            syncPrepareData();
        }
    }

    /**
     * 跟列表请求同步执行的数据获取
     */
    protected void syncPrepareData() {

    }

    @Override
    public void onRefresh() {  //下拉刷新
        refreshWithSwipe();
        getListData();
    }
    @Override
    protected void onNetWorkChange(boolean isConnect) {
        if(isConnect){
            refreshWithSwipe();
        }
        super.onNetWorkChange(isConnect);
    }
    @Override
    public void onLoadMoreRequested() {  //加载更多
        getListData();
    }

    @Override
    public void setListData(boolean isRefresh, List list, boolean isShowMore) {
        if (isRefresh) {
            baseRecycleViewAdapter.setNewData(list);
        } else {
            if (list != null && list.size() > 0) {
                baseRecycleViewAdapter.addData(list);
            }
        }
        if (isShowMore) {
            if (list != null && list.size() > 0) {
                baseRecycleViewAdapter.loadMoreComplete();
            } else {
                baseRecycleViewAdapter.loadMoreFail();
            }

        } else {
            baseRecycleViewAdapter.loadMoreEnd(!isShowMore);
        }
        resetView();
        hasData = true;  //数据载入完成后的标识
    }

    @Override
    public void setTotalCount(int totalCount) {

    }

    @Override
    public void showLoadFail(boolean hasNetWork) {
        if (baseRecycleViewAdapter == null) {
            return;
        }
        baseRecycleViewAdapter.clear();
        if (hasNetWork) {
            if (notDataView != null) {
                setHeaderFooterEmpty(true, true);
                baseRecycleViewAdapter.setEmptyView(notDataView);
            }
        } else {
            if (errorView != null) {
                setHeaderFooterEmpty(true, true);
                baseRecycleViewAdapter.setEmptyView(errorView);
            }
        }
        resetView();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_base_recycleview;
    }

    @Override
    protected void onStop() {
        super.onStop();

        hideProgressDialog();
    }

    @ApiUtils.UrlType
    protected String getUrlType() {
        return ApiUtils.DEFAULT;
    }

    /**
     * 设置请求地址
     *
     * @return
     */
    protected abstract String getUrl();

    /**
     * 设置请求参数
     *
     * @return
     */
    protected abstract Map<String, Object> getParams();

    /**
     * 返回参数类型
     *
     * @return
     */
    protected abstract Class<T> getResultClass();

    /**
     * 添加HeaderView
     *
     * @return
     */
    protected View addHeaderView() {
        return headerView;
    }

    /**
     * 添加FooterView
     *
     * @return
     */
    protected View addFooterView() {
        return footerView;
    }

    /**
     * 获取无数据时显示的View
     *
     * @return
     */
    protected View getNotDataView() {
        return notDataView;
    }

    /**
     * 设置无数据时显示的View
     *
     * @return
     */
    private void setNotDataView() {
        notDataView = getNotDataView();
        if (!isHideEmptyView()) {  //如果隐藏Empty则不初始化EmptyView
            if (notDataView == null) {
                notDataView = getLayoutInflater().inflate(R.layout.view_empty_lay, (ViewGroup) rvList.getParent(), false);
                ivNoData = notDataView.findViewById(R.id.iv_no_data);
                tvNoData = notDataView.findViewById(R.id.tv_no_data);
                btnInvite = notDataView.findViewById(R.id.btn_invite);
            }
//            notDataView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    refreshWithSwipe();
//                    onRefresh();
//                }
//            });
        }
    }
    public void setNotDataViewButtonText(CharSequence text){
        if(btnInvite == null){
            return;
        }
        btnInvite.setText(text);
        btnInvite.setVisibility(View.VISIBLE);
    }
    public void setNotDataText(String text) {
        if (tvNoData != null) {
            tvNoData.setText(text);
        }
    }

    /**
     * 设置无数据时显示的样式
     *
     * @param res
     */
    public void setNotDataImageRes(@DrawableRes int res) {
        if (ivNoData != null) {
            ivNoData.setImageResource(res);
        }
    }

    /**
     * 获取无网络时显示的View
     *
     * @return
     */
    protected View getErrorView() {
        return errorView;
    }

    /**
     * 设置无网络时显示的View
     *
     * @return
     */
    private void setErrorView() {
        errorView = getErrorView();
        if (errorView == null) {
            errorView = getLayoutInflater().inflate(R.layout.view_error_lay, (ViewGroup) rvList.getParent(), false);
        }
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshWithSwipe();
                onRefresh();
            }
        });
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new RecyclerViewNoBugLinearLayoutManager(this);
    }

    ;

    /**
     * 设置ListView的DividerHeight
     */
    protected int getDividerHeight() {
        return height;
    }

    /**
     * 设置Adapter
     *
     * @return
     */
    protected abstract BaseRecycleViewAdapter<T> getAdapter();


}
