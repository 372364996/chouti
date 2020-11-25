package com.chewawa.baselibrary.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.R2;
import com.chewawa.baselibrary.base.contract.BaseContract;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.event.DestroyEvent;
import com.chewawa.baselibrary.receiver.NetWorkListenerReceiver;
import com.chewawa.baselibrary.utils.BaseCommonUtil;
import com.chewawa.baselibrary.utils.DensityUtil;
import com.chewawa.baselibrary.utils.glide.GlideUtils;
import com.chewawa.baselibrary.view.InsureBar;
import com.chewawa.baselibrary.view.NoNetWorkView;
import com.chewawa.baselibrary.view.XProgressDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.chewawa.baselibrary.receiver.NetWorkListenerReceiver.ACTION_NETWORK_CHANGE;
import static com.chewawa.baselibrary.receiver.NetWorkListenerReceiver.KEY_NETWORK_STATE;


/**
 * 自定义Fragment父类
 *
 * @author nanfeifei
 * @version 1.0
 * @since 2016年8月1日下午6:15:00
 */
public abstract class NBaseFragment<H extends BasePresenterImpl> extends Fragment implements BaseContract.View{
    /**
     * 视图
     */
    protected View view;
    protected View netWork;
    protected LayoutInflater inflater;
    ViewGroup container;
    @Nullable
    @BindView(R2.id.toolbar_lay)
    public QMUITopBar toolbarLay;
    public SharedPreferences mSp;
    public String townId;
    public GlideUtils imageLoaderUtils;
    protected DisplayMetrics dm; // 用来取得手机屏幕宽高的对象
    protected String token;
    protected XProgressDialog loadingDialog;
    private Unbinder unbinder;
    public Activity activity;
    private MessageReceiver mMessageReceiver;
    private NetWorkListenerReceiver mNetworkChangeListener;
    boolean networkConnectStatus;  //网络连接状态
    public H lifecyclePresenter;
    public MaterialSearchView materialSearchView;
    ImmersionBar immersionBar;
    /**
     * 描述：返回
     */
    protected void back() {

    }
    protected boolean isViewInitFinished;
    public boolean isAlreadyVisible;
    private boolean isLazyLoading;

    public boolean isLazyLoading() {
        return isLazyLoading;
    }

    public void setLazyLoading(boolean lazyLoading) {
        isLazyLoading = lazyLoading;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!isLazyLoading||!isAlreadyVisible){ //当数据还未加载过时，此时才会加载
            requestData();
        }
        if(isResumed()){
            onVisibilityChangedToUser(isVisibleToUser, true);
        }

    }
    public void requestData(){
        if(isViewInitFinished && getUserVisibleHint()){//视图加载完成并且对用户可见
            prepareData();
            isAlreadyVisible = true; //已经首次加载（在本页面中切换时防止刷新，如果需要，可以去掉）
        }
    }
    /**
     * 描述：创建
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = BaseCommonUtil.getToken();
        activity = getActivity();
    }

    /**
     * 描述：加载视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        view = setContentView(inflater, container, savedInstanceState);
        return view;
    }

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract View setContentView(LayoutInflater inflater,
                                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        isViewInitFinished = true;  //View已经加载完成
//        initImmersionBar();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        imageLoaderUtils = new GlideUtils(this.getContext());
        //initTitleHeight();
        netWork = new NoNetWorkView(this.getContext());
        container.addView(netWork);
        netWork.setVisibility(View.GONE);
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        if (back != null) {
            back.setOnClickListener(v -> back());
        }
        lifecyclePresenter = initPresenter();
        if(lifecyclePresenter != null){ //生命周期管理
            getLifecycle().addObserver(lifecyclePresenter);
        }
        loadingDialog = new XProgressDialog(getActivity(), "加载中...");
        initView();
        initData();
        networkConnectStatus = true;
        requestData();
        if(isShowNetWorkSetting()){ //如果需要显示无网络时设置网络的视图再去注册广播
            registerNetWorkReceiver();
        }

    }
    public void initView() {
    }

    public void initData() {
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
     * 是否是透明状态栏
     */
    public boolean transparentStatusBar(){
        return false;
    }
    public boolean statusBarDarkFont(){
        return true;
    }
//    public void setTransparentStatusBar(boolean isTransparent, boolean isStatusBarDarkFont){
//        if(getActivity() instanceof NBaseActivity){
//            ((NBaseActivity)getActivity()).setTransparentStatusBar(isTransparent, isStatusBarDarkFont);
//        }
//    }
    /**
     * 重置状态栏高度，如果使用了搜索模块，则需要先初始化搜索栏
     * @param qmuiTopBar
     */
    public void resetStatusBarHeight(QMUITopBar qmuiTopBar){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = DensityUtil.dip2px(getActivity(), 48);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            layoutParams.setMargins(0, QMUIStatusBarHelper.getStatusbarHeight(getActivity())
                    , 0, 0);
        }
        qmuiTopBar.setLayoutParams(layoutParams);
        if(materialSearchView != null){
            materialSearchView.setLayoutParams(layoutParams);
        }
    }
    /**
     * 初始化Presenter（因需要兼容旧代码，故未写为抽象方法）
     * @return
     */
    public H initPresenter(){
        return null;
    }
    /**
     * 初始化SearchView,并初始化
     */
    public void initSearchView(View view, final OnSubmitSearchListener listener){
        materialSearchView = (MaterialSearchView)findViewById(R.id.sv_list_search);
        materialSearchView.setVisibility(View.VISIBLE);

        materialSearchView.setVoiceSearch(false);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                listener.onSubmitSearch(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String query) {
                //搜索文本变更
                if (query.length() == 0) {
                    listener.onSubmitSearch(query);
                }
                return true;
            }
        });
        view.setVisibility(View.VISIBLE);
        materialSearchView.setView(view);
    }
    public interface OnSubmitSearchListener{
        void onSubmitSearch(String query);
    }
    /**
     * 设置下拉刷新控件样式
     */
    protected void setSwipeRefreshStyle(SwipeRefreshLayout swipeRefresh){
        if(swipeRefresh == null){
            return;
        }
        swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity()
                .getApplicationContext(), 48));
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(getUserVisibleHint()){
            onVisibilityChangedToUser(true, false);
        }
//        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面("MainScreen"为页面名称，可自定义)
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(getUserVisibleHint()){
            onVisibilityChangedToUser(false, false);
        }
    }
    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     * @param isVisibleToUser true：用户能看见当前Fragment；false：用户看不见当前Fragment
     * @param isHappenedInSetUserVisibleHintMethod true：本次回调发生在setUserVisibleHintMethod方法里；false：发生在onResume或onPause方法里
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser, boolean isHappenedInSetUserVisibleHintMethod){
        if(isVisibleToUser){
            MobclickAgent.onPageStart(this.getClass().getSimpleName());
        }else{
            MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        }
    }
    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitFinished = false;
        isAlreadyVisible = false;
        isLazyLoading = false;

    }
    @Override
    public void onDestroy() {
        hideProgressDialog();
        super.onDestroy();
        if(isShowNetWorkSetting()){
            this.getContext().unregisterReceiver(mMessageReceiver);
            this.getContext().unregisterReceiver(mNetworkChangeListener);
        }
        unbinder.unbind();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DestroyEvent event) {

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
        this.getContext().registerReceiver(mNetworkChangeListener,filter);
        mMessageReceiver = new MessageReceiver();
        IntentFilter messageFilter = new IntentFilter();

        messageFilter.addAction(ACTION_NETWORK_CHANGE);
        this.getContext().registerReceiver(mMessageReceiver, messageFilter);
    }
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(ACTION_NETWORK_CHANGE)) {
                boolean isConnect = intent.getBooleanExtra(KEY_NETWORK_STATE, false);
                if(isConnect!=networkConnectStatus){
                    onNetWorkChange(isConnect);
                    networkConnectStatus = false;
                }

            }
        }

    }
    protected View findViewById(int id) {
        return view.findViewById(id);
    }


    /**
     * 显示加载进度条
     * Title: showProgressBar
     * Description:
     */
    @Override
    public void showProgressDialog() {
        if(this!=null&&this.getActivity()!=null&&!this.getActivity().isFinishing()){
            if (loadingDialog == null) {
                loadingDialog = new XProgressDialog(getActivity(), "加载中...");
            }
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

    }

    /**
     * 隐藏加载进度条
     * Title: showProgressBar
     * Description:
     */
    @Override
    public void hideProgressDialog() {
        if(this!=null&&this.getActivity()!=null&&!this.getActivity().isFinishing()){
            if (loadingDialog != null) {
                loadingDialog.cancel();
            }
        }
    }
    /**
     * 是否显示无网提示
     */
    public void showNoNetWork(boolean isShow) {

    }

    /**
     * Title: showNoData
     * Description: 无数据提示
     *
     * @param isShow   是否显示无数据提示
     * @param tipImg   无数据文字图片提示
     * @param tipText  无数据文字提示
     * @param isShowBt 无数据时是否显示按钮
     * @param tipBt    无数据时按钮提示
     */
    public void showNoData(boolean isShow, int tipImg, String tipText, boolean isShowBt, String tipBt) {

    }


    /**
     * 描述：设置标题
     *
     * @param text 标题
     */
    protected void setTitle(String text) {
        TextView tv_title = (TextView) findViewById(R.id.title);
        if (tv_title != null) {
            tv_title.setText(text);
        }
    }

    /**
     * 描述：设置图片标题
     *
     * @param drawable 图片
     */
    protected void setImageTitle(int drawable) {
        TextView tv_title = (TextView) findViewById(R.id.title);
        if (tv_title != null) {
            tv_title.setText("");
            tv_title.setBackgroundResource(drawable);
        }
    }

    /**
     * 描述:隐藏返回按钮
     */
    protected void backGone() {
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        if (back != null) {
            back.setVisibility(View.GONE);
        }
    }


    /**
     * 判断是否登陆
     * Title: isLogin
     * Description:
     *
     * @return
     */
    protected boolean isLogin() {
        String token = (String) getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE)
                .getString("Token", null);
        if (TextUtils.isEmpty(token)) {
            return false;
        } else {
            return true;
        }
    }

    protected void loginSuccess() {
    }


    protected void loginFail() {
    }

    protected void cityChange() {
    }

    /**
     * 描述：准备数据
     */
    protected abstract void prepareData();

    /**
     * 是否显示无网络时设置网络的视图
     * @return
     */
    protected boolean isShowNetWorkSetting(){
        return false;
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

}
