package com.chewawa.baselibrary.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.R2;
import com.chewawa.baselibrary.base.contract.DownloadContract;
import com.chewawa.baselibrary.base.presenter.DownloadPresenter;
import com.chewawa.baselibrary.utils.FileUtils;
import com.chewawa.baselibrary.utils.PermissionPageUtils;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.download.library.DownloadImpl;
import com.download.library.DownloadListenerAdapter;
import com.download.library.Extra;
import com.download.library.ResourceRequest;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.DefaultDownloadImpl;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.WebViewClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import butterknife.BindView;


/**
 * 父类WebView，支持微信支付，支付宝支付，图片上传
 * nanfeifei
 * 2017/12/26 14:52
 *
 * @version 1.0
 */
@SuppressLint("SetJavaScriptEnabled")
public abstract class BaseWebViewActivity extends NBaseActivity<DownloadPresenter> implements DownloadContract.View {
    protected AgentWeb mAgentWeb;
    @BindView(R2.id.ll_parent_lay)
    LinearLayout llParentLay;
    private String headPath = "";
    private Uri uritempFile;
    private String webTitle = "";
    QMUIDialog qmuiDialog;
    RxPermissions rxPermissions;
    String imagePath;
    int permissionStatus;
    public static final int PERMISSION_APPLY = 2001;
    public static final int PERMISSION_RETRY = 2002;
    public static final int PERMISSION_DENIED = 2003;

    @Override
    protected void initView() {
        initBar();
        insureBar.showFinish(View.VISIBLE);
        initWebView();
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_webview;
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(llParentLay, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))//
                .useDefaultIndicator(ContextCompat.getColor(this, R.color.transparent))//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .useMiddlewareWebClient(getMiddleWareWebClient())
                .useMiddlewareWebChrome(getMiddlewareWebChrome())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme AgentWeb 3.0.0 加入。
                .createAgentWeb()//
                .ready()
                .go(getUrl());
        setWebSetting();
        mAgentWeb.getUrlLoader().loadUrl(getUrl());
        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false);
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);
        rxPermissions = new RxPermissions(this);
        mAgentWeb.getWebCreator().getWebView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                WebView.HitTestResult result = ((WebView) view).getHitTestResult();
                if (null == result)
                    return false;
                int type = result.getType();
                // 这里可以拦截很多类型，我们只处理图片类型就可以了
                switch (type) {
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        imagePath = result.getExtra();
                        setTextAlertShow(PERMISSION_APPLY, getString(R.string.affirm_alert_save_image));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    public void setTextAlertShow(int permissionStatus, String title) {
        this.permissionStatus = permissionStatus;
        qmuiDialog = new QMUIDialog.MessageDialogBuilder(BaseWebViewActivity.this)
                .setMessage(title)
                .setCancelable(true)
                .setOnDecorationListener(new QMUIDialogView.OnDecorationListener() {
                    @Override
                    public void onDraw(Canvas canvas, QMUIDialogView view) {
                        if (PERMISSION_APPLY == permissionStatus) {
                            saveImage(imagePath);
                        } else if (PERMISSION_RETRY == permissionStatus) {
                            saveImage(imagePath);
                        } else {
                            try {
                                PermissionPageUtils.toPermissionSetting(BaseWebViewActivity.this);
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onDrawOver(Canvas canvas, QMUIDialogView view) {

                    }
                }).show();
    }

    public void saveImage(String imagePath) {
        rxPermissions
                .requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> { // will emit 1 Permission object
                    if (permission.granted) {
                        // All permissions are granted !
                        if (imagePath.startsWith("http")) {
                            presenter.downloadImage(imagePath, FileUtils.photoPath);
                        } else if (imagePath.contains("base64")) {
                            presenter.saveImage(imagePath, FileUtils.photoPath);
                        }

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        setTextAlertShow(PERMISSION_RETRY, getString(R.string.affirm_alert_image_permission_retry));
                    } else {
                        setTextAlertShow(PERMISSION_DENIED, getString(R.string.affirm_alert_image_permission_denied));
                    }
                });
    }

    @Override
    public DownloadPresenter initPresenter() {
        return new DownloadPresenter(this);
    }

    public void setWebSetting() {
    }

    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            /**
             * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
             * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.download.library:Downloader:4.1.1' ，
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param downloadListener
             * @return WebListenerManager
             */
            @Override
            public WebListenerManager setDownloader(WebView webView, android.webkit.DownloadListener downloadListener) {
                return super.setDownloader(webView,
                        new DefaultDownloadImpl((Activity) webView.getContext(),
                                webView,
                                this.mAgentWeb.getPermissionInterceptor()) {

                            @Override
                            protected ResourceRequest createResourceRequest(String url) {
                                return DownloadImpl.getInstance()
                                        .with(BaseWebViewActivity.this.getApplicationContext())
                                        .url(url)
                                        .quickProgress()
                                        .addHeader("", "")
                                        .setEnableIndicator(true)
                                        .autoOpenIgnoreMD5()
                                        .setRetry(5)
                                        .setBlockMaxTime(100000L);
                            }

                            @Override
                            protected void taskEnqueue(ResourceRequest resourceRequest) {
                                resourceRequest.enqueue(new DownloadListenerAdapter() {
                                    @Override
                                    public void onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, Extra extra) {
                                        super.onStart(url, userAgent, contentDisposition, mimetype, contentLength, extra);
                                    }

                                    @MainThread
                                    @Override
                                    public void onProgress(String url, long downloaded, long length, long usedTime) {
                                        super.onProgress(url, downloaded, length, usedTime);
                                    }

                                    @Override
                                    public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
                                        return super.onResult(throwable, path, url, extra);
                                    }
                                });
                            }
                        });
            }
        };
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            return super.shouldOverrideUrlLoading(view, request);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//      //判断重定向的方式一
//      WebView.HitTestResult hitTestResult = view.getHitTestResult();
//      if(hitTestResult == null||hitTestResult.getType() == WebView.HitTestResult.UNKNOWN_TYPE) {
//        insureBar.showFinish(View.VISIBLE);
//      }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
            showProgressDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            hideProgressDialog();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();  // 接受所有网站的证书
            handler.cancel();
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);

        }
    };

    protected @NonNull
    MiddlewareWebClientBase getMiddleWareWebClient() {
        return new MiddlewareWebClientBase() {

        };
    }

    protected @NonNull
    MiddlewareWebChromeBase getMiddlewareWebChrome() {
        return new MiddlewareWebChromeBase() {

        };
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
            //            Log.i("Info","progress:"+newProgress);
            if (newProgress > 80) {
                hideProgressDialog();
            }

        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            qmuiDialog = new QMUIDialog.MessageDialogBuilder(BaseWebViewActivity.this)
                    .setMessage(message)
                    .setCancelable(false)
                    .setOnDecorationListener(new QMUIDialogView.OnDecorationListener() {
                        @Override
                        public void onDraw(Canvas canvas, QMUIDialogView view) {
                            result.confirm();
                        }

                        @Override
                        public void onDrawOver(Canvas canvas, QMUIDialogView view) {

                        }
                    }).show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            qmuiDialog = new QMUIDialog.MessageDialogBuilder(BaseWebViewActivity.this)
                    .setMessage(message)
                    .setCancelable(false)
                    .setOnDecorationListener(new QMUIDialogView.OnDecorationListener() {
                        @Override
                        public void onDraw(Canvas canvas, QMUIDialogView view) {
                            result.confirm();
                        }

                        @Override
                        public void onDrawOver(Canvas canvas, QMUIDialogView view) {
                            result.cancel();
                        }
                    }).show();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (insureBar != null && !TextUtils.isEmpty(title) && !title.startsWith("http") && !title.startsWith("https")) {
                int length = title.length();
                if (length > 10) {
                    title = title.substring(0, 10).concat("...");
                }
            } else {
                title = getString(R.string.app_name);
            }
            webTitle = title;
            insureBar.setTitle(title);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    };

    public String getUrl() {

        return null;
    }

    @Override
    public void onResume() {
//    mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
//    mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onBack() {
        if (!mAgentWeb.back()) {
            BaseWebViewActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getUrlLoader().stopLoading();
        mAgentWeb.getAgentWebSettings().getWebSettings().setJavaScriptEnabled(false);
        mAgentWeb.clearWebCache();
        WebStorage.getInstance().deleteAllData();
        AgentWebConfig.clearDiskCache(this);
        WebView mWebview = mAgentWeb.getWebCreator().getWebView();
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
        //mAgentWeb.destroy();

    }

    @Override
    public void synchronizeToPhoto(String path) {
        ToastUtils.showToast(R.string.save_success);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        BaseWebViewActivity.this.sendBroadcast(intent);
    }
}
