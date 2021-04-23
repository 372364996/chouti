package com.hanzhi.onlineclassroom.ui.selectclass.fragment;

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
import com.hanzhi.onlineclassroom.bean.selectclass.ClassTabBean;
import com.hanzhi.onlineclassroom.ui.selectclass.contract.SelectClassContract;
import com.hanzhi.onlineclassroom.ui.selectclass.presenter.SelectClassPresenter;

import java.util.List;

import butterknife.BindView;

public class SelectClassFragment extends NBaseFragment<SelectClassPresenter> implements SelectClassContract.View, CommonTabPagerAdapter.TabPagerListener {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.sc_vp_main)
    ViewPager mViewPager;
    ClassApplyBean classApplyBean;
    private CommonTabPagerAdapter adapter;
    List<ClassTabBean> list;
    public static SelectClassFragment newInstance(ClassApplyBean classApplyBean) {
        SelectClassFragment selectClassFragment = new SelectClassFragment();
        Bundle args = new Bundle();
        args.putParcelable("classApplyBean", classApplyBean);
        selectClassFragment.setArguments(args);
        return selectClassFragment;
    }
    @Override
    protected View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selectclass, container, false);
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
    public SelectClassPresenter initPresenter() {
        return new SelectClassPresenter(this);
    }

    @Override
    public void setTabList(List<ClassTabBean> list, List<String> titleList) {
        this.list = list;
        adapter =
                new CommonTabPagerAdapter(getChildFragmentManager(), titleList.size()
                        , titleList,this.getContext());
        adapter.setListener(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(list.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public Fragment getFragment(int position) {
        return SelectClassChildFragment.newInstance(list.get(position).getId(), classApplyBean);
    }
}
