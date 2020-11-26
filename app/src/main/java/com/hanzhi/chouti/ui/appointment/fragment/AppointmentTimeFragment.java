package com.hanzhi.chouti.ui.appointment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chewawa.baselibrary.base.NBaseFragment;
import com.chewawa.baselibrary.view.viewpager.CommonTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.appointment.AppointmentTabBean;
import com.hanzhi.chouti.ui.appointment.adapter.YuYueFragmentPagerAdapter;
import com.hanzhi.chouti.ui.appointment.contract.AppointmentTimeContract;
import com.hanzhi.chouti.ui.appointment.presenter.AppointmentTimePresenter;
import com.hanzhi.chouti.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class AppointmentTimeFragment extends NBaseFragment<AppointmentTimePresenter> implements AppointmentTimeContract.View, CommonTabPagerAdapter.TabPagerListener {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.yy_vp_main)
    ViewPager mViewPager;
    YuYueFragmentPagerAdapter yuYueFragmentPagerAdapter;
    private CommonTabPagerAdapter adapter;
    List<AppointmentTabBean> list;
    @Override
    protected View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_yuyue, container, false);
        return root;
    }

    @Override
    public void initView() {
        super.initView();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式;
        Date d = new Date();//获取当前时间
//        List<Date> dateList = Utils.getBetweenDates(d, new Date(d.getTime() + 13 * 24 * 60 * 60 * 1000));
//        for (Date item : dateList) {
//            tabLayout.addTab(tabLayout.newTab().setText(Utils.dateToWeek(df.format(item))));
//        }
//        yuYueFragmentPagerAdapter = new YuYueFragmentPagerAdapter(getActivity().getSupportFragmentManager());
//        //使用适配器将ViewPager与Fragment绑定在一起
//        mViewPager.setAdapter(yuYueFragmentPagerAdapter);
//        //将TabLayout和ViewPager绑定在一起，相互影响，解放了开发人员对双方变动事件的监听。
//        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void prepareData() {
        lifecyclePresenter.getTabList();
    }

    @Override
    public AppointmentTimePresenter initPresenter() {
        return new AppointmentTimePresenter(this);
    }

    @Override
    public void setTabList(List<AppointmentTabBean> list, List<String> titleList) {
        this.list = list;
        adapter =
                new CommonTabPagerAdapter(getChildFragmentManager(), titleList.size()
                        , titleList,this.getContext());
        adapter.setListener(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(list.size());
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public Fragment getFragment(int position) {
        if(list == null){
            return AppointmentTimeChildFragment.newInstance("");
        }
        return AppointmentTimeChildFragment.newInstance(list.get(position).getDate());
    }
}
