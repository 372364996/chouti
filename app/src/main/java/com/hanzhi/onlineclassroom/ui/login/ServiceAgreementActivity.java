package com.hanzhi.onlineclassroom.ui.login;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.onlineclassroom.R;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @class 用户服务协议
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 13:58
 */
public class ServiceAgreementActivity extends NBaseActivity {
    @BindView(R.id.toolbar_lay)
    QMUITopBar toolbarLay;
    @BindView(R.id.wv_service_agreement)
    WebView wvServiceAgreement;

    @Override
    public int initLoadResId() {
        return R.layout.activity_service_agreement;
    }

    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_service_agreement);
        //设置自适应屏幕
        WebSettings settings = wvServiceAgreement.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //访问网页
        wvServiceAgreement.loadUrl("http://hanzhiapp.hdlebaobao.cn/home/agreement");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        wvServiceAgreement.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });

    }


}
