package com.hanzhi.onlineclassroom.ui.mine.fragment;

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
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.ClassApplyBean;
import com.hanzhi.onlineclassroom.ui.appointment.fragment.SetAppointmentTimeFragment;
import com.hanzhi.onlineclassroom.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MineFragment extends NBaseFragment implements CommonTabPagerAdapter.TabPagerListener {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.sc_vp_main)
    ViewPager mViewPager;
    private CommonTabPagerAdapter adapter;
    int position;
    ClassApplyBean classApplyBean;
    public static MineFragment newInstance() {
        return newInstance(0);
    }

    public static MineFragment newInstance(int position) {
        MineFragment mineFragment = new MineFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        mineFragment.setArguments(args);
        return mineFragment;
    }

    @Override
    protected View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        return root;
    }

    @Override
    public void initView() {
        super.initView();
        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
        }
        List<String> titleList = new ArrayList<>();
        if (CommonUtil.getTeacherId() > 0) {
            titleList.add(getString(R.string.mine_table_my_class));
            titleList.add(getString(R.string.mine_table_class_time));
        } else {
            titleList.add(getString(R.string.mine_table_my_class));
            titleList.add(getString(R.string.mine_table_collect_teacher));
            titleList.add(getString(R.string.mine_table_my_profile));
            titleList.add(getString(R.string.mine_table_my_wallet));
        }

        adapter = new CommonTabPagerAdapter(getChildFragmentManager(), titleList.size()
                , titleList, getActivity());
        adapter.setListener(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(titleList.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mViewPager.setCurrentItem(position);
    }

    @Override
    protected void prepareData() {
    }


    @Override
    public Fragment getFragment(int position) {

        if (position == 0) {
            return MyClassFragment.newInstance();
        }
        if (CommonUtil.getTeacherId() > 0) {
            if (position == 1) {
                classApplyBean=new ClassApplyBean();
                classApplyBean.setTeacherId(CommonUtil.getTeacherId());
                return SetAppointmentTimeFragment.newInstance(classApplyBean);
            }

        } else {
            if (position == 1) {
                return CollectTeacherFragment.newInstance();
            }
            if (position == 2) {
                return MyProfileFragment.newInstance();
            }
            if (position == 3) {
                return WalletFragment.newInstance();
            }
        }

        return MyProfileFragment.newInstance();
    }

}
