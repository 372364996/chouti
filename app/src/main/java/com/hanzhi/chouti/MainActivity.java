package com.hanzhi.chouti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.hanzhi.chouti.ui.appointment.fragment.AppointmentTimeFragment;
import com.hanzhi.chouti.ui.login.LoginActivity;
import com.hanzhi.chouti.ui.login.RegisterActivity;
import com.hanzhi.chouti.ui.mine.fragment.MineFragment;
import com.hanzhi.chouti.ui.selectclass.fragment.SelectClassFragment;
import com.hanzhi.chouti.ui.teachers.fragment.TeacherFragment;
import com.hanzhi.chouti.utils.CommonUtil;
import com.hjm.bottomtabbar.BottomTabBar;
import com.hjm.bottomtabbar.custom.CustomFragmentTabHost;
import com.qmuiteam.qmui.widget.QMUITopBar;

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
    public static void startMainActivity(FragmentActivity activity, int pos) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("tab_index", pos);
        activity.startActivity(intent);
    }
    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            tabIndex = intent.getIntExtra("tab_index", 0);
            ((CustomFragmentTabHost)findViewById(android.R.id.tabhost)).setCurrentTab(tabIndex);
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
        toolbarLay.addRightImageButton(R.drawable.ic_action_qianbao, R.id.home_toolbar_wallet);
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
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_slideshow7:
                        SharedPreferences.Editor editor = getSharedPreferences("LoginInfo", MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_slideshow6:
                        intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    default:
                        break;
                }
                return true;
            }
        });
        //初始化Fragment
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_toolbar_drawer:{
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            }
        }
    }
}
