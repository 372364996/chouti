package com.hanzhi.onlineclassroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.hanzhi.onlineclassroom.ui.appointment.fragment.AppointmentTimeFragment;
import com.hanzhi.onlineclassroom.ui.login.AboutActivity;
import com.hanzhi.onlineclassroom.ui.login.LoginActivity;
import com.hanzhi.onlineclassroom.ui.login.RegisterActivity;
import com.hanzhi.onlineclassroom.ui.mine.MineActivity;
import com.hanzhi.onlineclassroom.ui.mine.WalletActivity;
import com.hanzhi.onlineclassroom.ui.mine.fragment.MineFragment;
import com.hanzhi.onlineclassroom.ui.mine.fragment.MyClassFragment;
import com.hanzhi.onlineclassroom.ui.selectclass.fragment.SelectClassFragment;
import com.hanzhi.onlineclassroom.ui.teachers.fragment.TeacherFragment;
import com.hanzhi.onlineclassroom.utils.CommonUtil;
import com.hjm.bottomtabbar.BottomTabBar;
import com.hjm.bottomtabbar.custom.CustomFragmentTabHost;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_lay)
    QMUITopBar toolbarLay;
    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    int tabIndex;
    private Intent intent;
    QMUIDialog qmuiDialog;
    private int mCurrentDialogStyle = R.style.DialogTheme2;

    public static void startMainActivity(FragmentActivity activity, int pos) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("tab_index", pos);
        activity.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            tabIndex = intent.getIntExtra("tab_index", 0);
            ((CustomFragmentTabHost) findViewById(android.R.id.tabhost)).setCurrentTab(tabIndex);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!CommonUtil.isLogin()) {
            // Toast.makeText(getBaseContext(),"没有登陆",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            super.startActivity(intent);
            finish();
        }
        toolbarLay.addLeftImageButton(R.drawable.ic_action_menu, R.id.home_toolbar_drawer)
                .setOnClickListener(this);
        toolbarLay.addRightImageButton(R.drawable.ic_home_qianbao, R.id.home_toolbar_wallet).setOnClickListener(this);
        toolbarLay.setTitle(R.string.main_tab_teacher);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);                   //传入ToolBar实例
//        ActionBar actionBar = getSupportActionBar();    //得到ActionBar实例
//        if (actionBar != null) {
//            //显示导航按钮
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            //设置导航按钮图片
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
//        }
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setDrawerLayout(drawerLayout)
                .build();
//        NavigationView navigationView = findViewById(R.id.nav_view);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//      //  NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        TextView tvUserName = navView.getHeaderView(0).findViewById(R.id.tvUserName);
        tvUserName.setText(CommonUtil.getUserName());
        ImageView ivUserHeadImg = navView.getHeaderView(0).findViewById(R.id.ivUserHeadImg);

        Glide.with(this).load(CommonUtil.getUserHeadImg()).into(ivUserHeadImg);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        MineActivity.start(MainActivity.this, 0);
//                        ((CustomFragmentTabHost)findViewById(android.R.id.tabhost)).setCurrentTab(3);
                        break;
                    case R.id.nav_gallery:
                        MineActivity.start(MainActivity.this, 1);
//                        ((CustomFragmentTabHost)findViewById(android.R.id.tabhost)).setCurrentTab(3);
                        break;
                    case R.id.nav_slideshow:
                        MineActivity.start(MainActivity.this, 2);
//                        ((CustomFragmentTabHost)findViewById(android.R.id.tabhost)).setCurrentTab(3);
                        break;
                    case R.id.nav_slideshow1:
                        MineActivity.start(MainActivity.this, 3);
//                        ((CustomFragmentTabHost)findViewById(android.R.id.tabhost)).setCurrentTab(3);
                        break;
                    case R.id.nav_slideshow7:
                        CommonUtil.clearUserData();
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_slideshow4:
                        intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_slideshow3:
                        qmuiDialog = new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                                .setSkinManager(QMUISkinManager.defaultInstance(MainActivity.this))
                                .setTitle(R.string.dialog_title)
                                .setMessage(R.string.clear_cache)
                                .addAction(getString(R.string.wallet_to_weichat_cancel), new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                })
                                .addAction(getString(R.string.wallet_to_weichat_affirm), new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                        Toast.makeText(getBaseContext(), "缓存已清理", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .create(mCurrentDialogStyle);
                        qmuiDialog.show();
                    default:
                        break;
                }
                return true;
            }
        });
        if (CommonUtil.getTeacherId() > 0) {
            bottomTabBar.init(getSupportFragmentManager())
                    .setImgSize(50, 50)   //图片大小
                    .setFontSize(12)//字体大小
                    .setTabPadding(20, 6, 10)//选项卡的间距
                    .addTabItem("连接课程", R.drawable.ic_teacher_tab, MyClassFragment.class)
                    // .addTabItem("记录", R.drawable.ic_yuyue_tab, AppointmentTimeFragment.class)
                    .addTabItem("教材", R.drawable.ic_selectclass_tab, SelectClassFragment.class)
                    .addTabItem("我的", R.drawable.ic_mine_tab, MineFragment.class)
                    .isShowDivider(true)  //是否包含分割线
                    .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                        @Override
                        public void onTabChange(int position, String name) {
                            Log.i("TGA", "位置：" + position + "   选项卡：" + name);
                            toolbarLay.setTitle(name);
                        }
                    });
        } else {
            bottomTabBar.init(getSupportFragmentManager())
                    .setImgSize(50, 50)   //图片大小
                    .setFontSize(12)//字体大小
                    .setTabPadding(20, 6, 10)//选项卡的间距
                    .addTabItem(getString(R.string.main_tab_teacher), R.drawable.ic_teacher_tab, TeacherFragment.class)
                    .addTabItem("预约", R.drawable.ic_yuyue_tab, AppointmentTimeFragment.class)
                    .addTabItem("选择课程", R.drawable.ic_selectclass_tab, SelectClassFragment.class)
                    .addTabItem("我的", R.drawable.ic_mine_tab, MineFragment.class)
                    .isShowDivider(true)  //是否包含分割线
                    .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                        @Override
                        public void onTabChange(int position, String name) {
                            Log.i("TGA", "位置：" + position + "   选项卡：" + name);
                            toolbarLay.setTitle(name);
                        }
                    });
        }
        //初始化Fragment

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_toolbar_drawer:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.home_toolbar_wallet:
                Log.i("qianbao", "点击了钱包按钮");
                Intent intent = new Intent(this, WalletActivity.class);
                super.startActivity(intent);
                break;
        }
    }
//    测试git命令
}
