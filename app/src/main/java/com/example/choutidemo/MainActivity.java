package com.example.choutidemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.choutidemo.ui.gallery.GalleryFragment;
import com.example.choutidemo.ui.home.HomeFragment;
import com.example.choutidemo.ui.login.LoginActivity;
import com.example.choutidemo.ui.slideshow.SlideshowFragment;
import com.example.choutidemo.ui.slideshow.SlideshowFragment1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.hjm.bottomtabbar.BottomTabBar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private BottomTabBar bottomTabBar;

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
        }
        SharedPreferences.Editor prefsEditor = appPrefs.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_slideshow1)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        bottomTabBar = findViewById(R.id.bottom_tab_bar);
        //初始化Fragment
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50, 50)   //图片大小
                .setFontSize(12)            //字体大小
                .setTabPadding(4, 6, 10)//选项卡的间距
                .addTabItem("首页", R.drawable.hanzhilogo, GalleryFragment.class)
                .addTabItem("分类", R.drawable.tabimg, HomeFragment.class)
                .addTabItem("发现", R.drawable.ic_menu_camera, SlideshowFragment.class)
                .addTabItem("我的", R.drawable.ic_menu_camera, SlideshowFragment1.class)
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
}
