package com.hanzhi.onlineclassroom.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.mine.ClassCardBean;
import com.hanzhi.onlineclassroom.bean.mine.UsInfoBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.mine.adapter.WalletCardAdapter;
import com.hanzhi.onlineclassroom.ui.mine.contract.WalletContract;
import com.hanzhi.onlineclassroom.ui.mine.presenter.WalletPresenter;
import com.hanzhi.onlineclassroom.view.ConnectUsDialog;

import java.util.Map;

/**
 * @class 我的钱包
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class WalletFragment extends BaseRecycleViewFragment<ClassCardBean> implements View.OnClickListener, WalletContract.View {
    Button btnConnectUs;
    WalletPresenter walletPresenter;
    ConnectUsDialog connectUsDialog;
    public static WalletFragment newInstance() {
        WalletFragment myClassChildFragment = new WalletFragment();
        Bundle args = new Bundle();
        myClassChildFragment.setArguments(args);
        return myClassChildFragment;
    }
    @Override
    protected String getUrl() {
        return Constants.GET_MY_WALLET_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.put("size", 20);
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        setEnableLoadMore(false);
        connectUsDialog = new ConnectUsDialog(getActivity());
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        walletPresenter.getUsInfo();
    }

    @Override
    public BasePresenterImpl initPresenter() {
        walletPresenter = new WalletPresenter(this);
        return super.initPresenter();
    }

    @Override
    protected Class<ClassCardBean> getResultClass() {
        return ClassCardBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter<ClassCardBean> getAdapter() {
        return new WalletCardAdapter();
    }

    @Override
    protected View addFooterView() {
        footerView = inflater.inflate(R.layout.view_footer_wallect, null);
        btnConnectUs = footerView.findViewById(R.id.btn_connect_us);
        btnConnectUs.setOnClickListener(this);
        return footerView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_connect_us:{
                connectUsDialog.show();
                break;
            }
        }
    }

    @Override
    public void setUserInfo(UsInfoBean usInfoBean) {
        if(connectUsDialog == null){
            connectUsDialog = new ConnectUsDialog(getActivity());
        }
        connectUsDialog.setWeichat1(usInfoBean.getWeiXin1());
        connectUsDialog.setWeichat2(usInfoBean.getWeiXin2());
        connectUsDialog.setPhone(usInfoBean.getTel());
        connectUsDialog.setWebUrl(usInfoBean.getOfficialWebsite());
    }
}
