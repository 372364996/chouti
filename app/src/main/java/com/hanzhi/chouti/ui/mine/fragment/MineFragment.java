package com.hanzhi.chouti.ui.mine.fragment;

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
import com.hanzhi.chouti.bean.mine.MyClassTabBean;
import com.hanzhi.chouti.ui.mine.contract.MyClassContract;
import com.hanzhi.chouti.ui.mine.presenter.MyClassPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MineFragment extends NBaseFragment implements CommonTabPagerAdapter.TabPagerListener{

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.sc_vp_main)
    ViewPager mViewPager;
    private CommonTabPagerAdapter adapter;
    public static MineFragment newInstance() {
        MineFragment mineFragment = new MineFragment();
        Bundle args = new Bundle();
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
        List<String> titleList = new ArrayList<>();
        titleList.add(getString(R.string.mine_table_my_class));
        titleList.add(getString(R.string.mine_table_collect_teacher));
        titleList.add(getString(R.string.mine_table_my_profile));
        titleList.add(getString(R.string.mine_table_my_wallet));
        adapter = new CommonTabPagerAdapter(getChildFragmentManager(), titleList.size()
                        , titleList, getActivity());
        adapter.setListener(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(titleList.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void prepareData() {
    }


    @Override
    public Fragment getFragment(int position) {
        if(position == 0){
            return MyClassFragment.newInstance();
        }else if(position == 1){
            return MyClassFragment.newInstance();
        }else if(position == 2){
            MyProfileFragment.newInstance();
        }else if(position == 3){
            return MyClassFragment.newInstance();
        }
        return MyProfileFragment.newInstance();
    }

}