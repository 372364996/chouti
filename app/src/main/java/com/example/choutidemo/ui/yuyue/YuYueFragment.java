package com.example.choutidemo.ui.yuyue;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.choutidemo.MainActivity;
import com.example.choutidemo.R;
import com.example.choutidemo.ui.yuyue.YuYueFragmentPagerAdapter;
import com.example.choutidemo.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.hjm.bottomtabbar.BottomTabBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YuYueFragment extends Fragment {

    private YuYueViewModel yuYueViewModel;
    private BottomTabBar bottomTabBar;
    TabLayout tabLayout;
    ViewPager mViewPager;
    YuYueFragmentPagerAdapter yuYueFragmentPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_yuyue, container, false);
        tabLayout = root.findViewById(R.id.tabs);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式;
        Date d = new Date();//获取当前时间
        List<Date> dateList = Utils.getBetweenDates(d, new Date(d.getTime() + 13 * 24 * 60 * 60 * 1000));
        for (Date item : dateList) {
            tabLayout.addTab(tabLayout.newTab().setText(Utils.dateToWeek(df.format(item))));
        }
        mViewPager = root.findViewById(R.id.yy_vp_main);
        yuYueFragmentPagerAdapter = new YuYueFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager.setAdapter(yuYueFragmentPagerAdapter);
        //将TabLayout和ViewPager绑定在一起，相互影响，解放了开发人员对双方变动事件的监听
        tabLayout.setupWithViewPager(mViewPager);
        return root;
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        if (toolbar != null)
            toolbar.setTitle("预约");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


}
