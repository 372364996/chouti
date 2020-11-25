package com.hanzhi.chouti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;

import com.hanzhi.chouti.ui.teachers.ListViewAdapter;
import com.hanzhi.chouti.ui.yuyue.YuYueFragment;
import com.hanzhi.chouti.ui.login.LoginActivity;
import com.hanzhi.chouti.ui.login.RegisterActivity;
import com.hanzhi.chouti.ui.mine.MineFragment;
import com.hanzhi.chouti.ui.selectclass.SelectClassFragment;
import com.hanzhi.chouti.ui.teachers.TeacherFragment;
import com.google.android.material.navigation.NavigationView;
import com.hjm.bottomtabbar.BottomTabBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements ListViewAdapter.InnerItemOnclickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private BottomTabBar bottomTabBar;
    private DrawerLayout drawerLayout;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*判断是否登陆,获取存储在SharedPreferences中的信息*/
        SharedPreferences appPrefs = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        String userName = appPrefs.getString("userName", "");
        if (userName == null || userName == "") {
            // Toast.makeText(getBaseContext(),"没有登陆",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            super.startActivity(intent);
            finish();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);                   //传入ToolBar实例
        ActionBar actionBar = getSupportActionBar();    //得到ActionBar实例
        if (actionBar != null){
            //显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航按钮图片
           actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

        NavigationView navigationView = findViewById(R.id.nav_view);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//      //  NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
        bottomTabBar = findViewById(R.id.bottom_tab_bar);
        //初始化Fragment
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50, 50)   //图片大小
                .setFontSize(12)//字体大小
                .setTabPadding(20, 6, 10)//选项卡的间距
                .addTabItem("外教", R.drawable.ic_teacher_tab, TeacherFragment.class)
                .addTabItem("预约", R.drawable.ic_yuyue_tab, YuYueFragment.class)
                .addTabItem("选择课程", R.drawable.ic_selectclass_tab, SelectClassFragment.class)
                .addTabItem("我的", R.drawable.ic_mine_tab, MineFragment.class)
                .isShowDivider(true)  //是否包含分割线
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {
                        Log.i("TGA", "位置：" + position + "   选项卡：" + name);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void itemClick(View v) {

    }
}
