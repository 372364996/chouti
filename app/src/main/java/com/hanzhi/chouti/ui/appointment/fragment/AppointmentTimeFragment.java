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
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.appointment.AppointmentTabBean;
import com.hanzhi.chouti.ui.appointment.contract.AppointmentTimeContract;
import com.hanzhi.chouti.ui.appointment.presenter.AppointmentTimePresenter;

import java.util.List;

import butterknife.BindView;

public class AppointmentTimeFragment extends NBaseFragment<AppointmentTimePresenter> implements AppointmentTimeContract.View, CommonTabPagerAdapter.TabPagerListener {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.yy_vp_main)
    ViewPager mViewPager;
    private CommonTabPagerAdapter adapter;
    List<AppointmentTabBean> list;
    ClassApplyBean classApplyBean;
    public static AppointmentTimeFragment newInstance(ClassApplyBean classApplyBean) {
        AppointmentTimeFragment appointmentTimeFragment = new AppointmentTimeFragment();
        Bundle args = new Bundle();
        args.putParcelable("classApplyBean", classApplyBean);
        appointmentTimeFragment.setArguments(args);
        return appointmentTimeFragment;
    }
    @Override
    protected View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_yuyue, container, false);
        return root;
    }

    @Override
    public void initView() {
        super.initView();
        if(getArguments() != null){
            classApplyBean = getArguments().getParcelable("classApplyBean");
        }
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
            return AppointmentTimeChildFragment.newInstance("", classApplyBean);
        }
        return AppointmentTimeChildFragment.newInstance(list.get(position).getDate(), classApplyBean);
    }
}