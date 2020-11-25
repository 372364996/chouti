package com.chewawa.baselibrary.base;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.R2;
import com.chewawa.baselibrary.base.contract.BaseContract;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.event.DestroyEvent;
import com.chewawa.baselibrary.receiver.NetWorkListenerReceiver;
import com.chewawa.baselibrary.utils.BaseCommonUtil;
import com.chewawa.baselibrary.utils.DensityUtil;
import com.chewawa.baselibrary.utils.PermissionPageUtils;
import com.chewawa.baselibrary.utils.SPConstants;
import com.chewawa.baselibrary.utils.glide.GlideUtils;
import com.chewawa.baselibrary.view.InsureBar;
import com.chewawa.baselibrary.view.NoNetWorkView;
import com.chewawa.baselibrary.view.XProgressDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chewawa.baselibrary.receiver.NetWorkListenerReceiver.ACTION_NETWORK_CHANGE;
import static com.chewawa.baselibrary.receiver.NetWorkListenerReceiver.KEY_NETWORK_STATE;


/**
 * Activity父类（继承其他父类的尽量慢慢修改为此类）
 * nanfeifei
 * 2016/11/23 15:15
 *
 * @version 1.0
 */
public abstract class NBaseActivity<H extends BasePresenterImpl> extends AppCompatActivity implements BaseContract.View{
    private final String TAG = "MPermissions";
    private int REQUEST_CODE_PERMISSION = 0x00099;
    public boolean flag;
    protected int destroyFrom;
    public InsureBar insureBar;
    @Nullable
    @BindView(R2.id.toolbar_lay)
    public QMUITopBar toolbarLay;
    public MaterialSearchView materialSearchView;
    protected View netWork;
    public GlideUtils imageLoaderUtils;
    protected XProgressDialog loadingDialog;
    protected String token;
    public H presenter;
    ImmersionBar immersionBar;
    private MessageReceiver mMessageReceiver;
    private NetWorkListenerReceiver mNetworkChangeListener;
    boolean networkConnectStatus;  //网络连接状态
    /**
     * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeSuperOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        beforeContentView(savedInstanceState);
        if(initLoadResId()!=0){
            setContentView(initLoadResId());
            ButterKnife.bind(this);
        }
        initImmersionBar();
        EventBus.getDefault().register(this);
        token = BaseCommonUtil.getToken();
        imageLoaderUtils = new GlideUtils(this);
        presenter = initPresenter();
        if(presenter != null){ //生命周期管理
            getLifecycle().addObserver(presenter);
        }
        netWork = new NoNetWorkView(this);
        ViewGroup view =  (ViewGroup)this.getWindow().getDecorView();
        view.addView(netWork);
        netWork.setVisibility(View.GONE);
        initView();
        initData();
        networkConnectStatus = true;
        prepareData();

        if(isShowNetWorkSetting()){ //如果需要显示无网络时设置网络的视图再去注册广播
            registerNetWorkReceiver();
        }
    }
    public void beforeSuperOnCreate(Bundle savedInstanceState){

    }
    public void initImmersionBar(){
        immersionBar = ImmersionBar.with(this);
        setTransparentStatusBar(transparentStatusBar(), statusBarDarkFont());
    }

    /**
     *
     * @param isTransparent
     * @param isStatusBarDarkFont
     */
    public void setTransparentStatusBar(boolean isTransparent, boolean isStatusBarDarkFont){
        if(immersionBar == null){
            return;
        }

        immersionBar.reset();
        if(isTransparent){
            immersionBar.statusBarColor(R.color.transparent)
                    .fitsSystemWindows(false) ;   //解决状态栏和布局重叠问题
        }else {
            immersionBar.statusBarColor(R.color.colorPrimary)
                    .fitsSystemWindows(true) ;   //解决状态栏和布局重叠问题
        }
        if(isStatusBarDarkFont){
            immersionBar.statusBarDarkFont(true, 0.2f); //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        }else {
            immersionBar.statusBarDarkFont(false, 0.2f);
        }
        immersionBar.init();

    }
    /**
     * 初始化SearchView,并初始化
     */
    public void initSearchView(final OnSubmitSearchListener listener){
//        materialSearchView = findViewById(R.id.sv_list_search);
//        materialSearchView.setVisibility(View.VISIBLE);
//
//        materialSearchView.setVoiceSearch(false);
//        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override public boolean onQueryTextSubmit(String query) {
//                listener.onSubmitSearch(query);
//                return true;
//            }
//
//            @Override public boolean onQueryTextChange(String query) {
//                //搜索文本变更
//                if (query.length() == 0) {
//                    listener.onSubmitSearch(query);
//                }
//                return true;
//            }
//        });
//        materialSearchView.setView(insureBar.getRight());
    }
    public interface OnSubmitSearchListener{
        void onSubmitSearch(String query);
    }
    /**
     * 是否是透明状态栏
     */
    public boolean transparentStatusBar(){
        return false;
    }
    public boolean statusBarDarkFont(){
        return true;
    }
    public void initBar() {
        insureBar = new InsureBar(this);
        insureBar.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
    }
    public void initToolBar(){
        if(toolbarLay == null){
            return;
        }
        toolbarLay.addLeftImageButton(R.drawable.ic_toolbar_back, R.id.toolbar_left_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
    }
    /**
     * 设置下拉刷新控件样式
     */
    protected void setSwipeRefreshStyle(SwipeRefreshLayout swipeRefresh){
        if(swipeRefresh == null){
            return;
        }
        swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(this
                .getApplicationContext(), 48));
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }
  /**
   * 设置页面标题
   * @param titleRes
   */
  public void setBarTitle(@StringRes int titleRes){
    if(insureBar != null){
      insureBar.setTitle(getString(titleRes));
    }
  }
  /**
   * 设置页面标题
   * @param title
   */
  public void setBarTitle(String title){
    if(insureBar != null){
      insureBar.setTitle(title);
    }
  }
    /**
     * 设置页面标题
     * @param title
     */
    public void setBarTitle(String title, @ColorRes int color){
        if(insureBar != null){
            insureBar.setTitle(title);
            insureBar.setTitleColor(color);
        }
    }
    /**
     * 设置标题栏背景色
     * @param color
     */
    public void setBarBackgroundColor(@ColorRes int color){
        if(insureBar != null){
            insureBar.setBackgroundColor(color);
        }
    }

    /**
     * 设置返回键图片
     * @param resId
     */
    public void setBackImage(@DrawableRes int resId){
        if(insureBar != null){
            insureBar.getBack().setImageResource(resId);
        }
    }

    /**
     * 隐藏返回按钮
     */
    public void setBackGone(){
        if(insureBar != null){
            insureBar.hideBack();
        }
    }

    /**

    /**
     * setContentView之前执行的事件
     */
    protected void beforeContentView(Bundle savedInstanceState) {

    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    @LayoutRes
    public abstract int initLoadResId();

    protected abstract void initView();

    /**
     * 描述：获取数据源（网络请求或者数据库读取）
     */
    protected void prepareData() {
    }

    /**
     * 显示加载进度框
     */
    @Override
    public void showProgressDialog() {
        if(!this.isFinishing()){
            if (loadingDialog == null) {
                loadingDialog = new XProgressDialog(this);
            }
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

    }
    /**
     * 设置加载框提示文本
     */
    public void showProgressDialog(String alertText) {
        if(!this.isFinishing()){
            if (loadingDialog == null) {
                loadingDialog = new XProgressDialog(this);
            }
            loadingDialog.setMessage(alertText);
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

    }
    /**
     * 隐藏加载进度框
     */
    @Override
    public void hideProgressDialog() {
        if(!this.isFinishing()){
            if (loadingDialog != null) {
                loadingDialog.cancel();
            }
        }

    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

        /**
         * 返回事件
         */
    public void onBack(){
        this.finish();
    }
    /**
     * 初始化Presenter（因需要兼容旧代码，故未写为抽象方法）
     * @return
     */
    public H initPresenter(){
        return null;
    }


    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DestroyEvent event) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        token = BaseCommonUtil.getToken();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        hideProgressDialog();
        if(presenter!=null){
            presenter.detachView();
        }
        super.onDestroy();
        if(isShowNetWorkSetting()){
            this.unregisterReceiver(mMessageReceiver);
            this.unregisterReceiver(mNetworkChangeListener);
        }
        EventBus.getDefault().unregister(this);

    }
    /**
     * 动态注册广播
     */
    public void registerNetWorkReceiver() {
        mNetworkChangeListener = new NetWorkListenerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        //filter.addAction("android.net.wifi.STATE_CHANGE");
        this.registerReceiver(mNetworkChangeListener,filter);
        mMessageReceiver = new MessageReceiver();
        IntentFilter messageFilter = new IntentFilter();

        messageFilter.addAction(ACTION_NETWORK_CHANGE);
        this.registerReceiver(mMessageReceiver, messageFilter);
    }
    /**
     * 是否显示无网络时设置网络的视图
     * @return
     */
    protected boolean isShowNetWorkSetting(){
        return false;
    }
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(ACTION_NETWORK_CHANGE)) {
                boolean isConnect = intent.getBooleanExtra(KEY_NETWORK_STATE, false);
                if(isConnect!=networkConnectStatus){
                    onNetWorkChange(isConnect);
                    networkConnectStatus = isConnect;
                }

            }
        }

    }
    /**
     * 网络连接状态发生变化的回调
     * @param isConnect
     */
    protected void onNetWorkChange(boolean isConnect){
        if(isConnect){
            if(netWork!=null){
                netWork.setVisibility(View.GONE);
            }
            prepareData();
        }else {
            if(netWork!=null){
                netWork.setVisibility(View.VISIBLE);
            }
        }
    }
    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
