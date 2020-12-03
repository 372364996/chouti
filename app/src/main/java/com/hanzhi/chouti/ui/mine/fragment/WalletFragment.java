package com.hanzhi.chouti.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.mine.ClassCardBean;
import com.hanzhi.chouti.bean.mine.MyClassBean;
import com.hanzhi.chouti.bean.mine.MyClassTabBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.appointment.AppointmentTimeActivity;
import com.hanzhi.chouti.ui.mine.MyClassDetailActivity;
import com.hanzhi.chouti.ui.mine.adapter.MyClassAdapter;
import com.hanzhi.chouti.ui.mine.adapter.WalletCardAdapter;
import com.hanzhi.chouti.ui.mine.contract.MyClassContract;
import com.hanzhi.chouti.ui.mine.presenter.MyClassPresenter;
import com.hanzhi.chouti.view.ConnectUsDialog;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;
import java.util.Map;

/**
 * @class 我的钱包
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class WalletFragment extends BaseRecycleViewFragment<ClassCardBean> implements View.OnClickListener {
    Button btnConnectUs;
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
    public BasePresenterImpl initPresenter() {
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
}
