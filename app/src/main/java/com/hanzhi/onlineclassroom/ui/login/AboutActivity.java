package com.hanzhi.onlineclassroom.ui.login;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.onlineclassroom.R;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

/**
 * @class 用户服务协议
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 13:58
 */
public class AboutActivity extends NBaseActivity {
    @BindView(R.id.toolbar_lay)
    QMUITopBar toolbarLay;
    @BindView(R.id.wv_about)
    WebView wvAbout;

    @Override
    public int initLoadResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_about);
        //设置自适应屏幕
        WebSettings settings = wvAbout.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);

        //访问网页
        wvAbout.loadUrl("https://www.hanzhistudy.com/about-company/");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        wvAbout.setWebViewClient(new WebViewClient() {
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
