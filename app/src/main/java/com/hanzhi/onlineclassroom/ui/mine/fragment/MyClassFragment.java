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
import com.hanzhi.onlineclassroom.bean.mine.MyClassTabBean;
import com.hanzhi.onlineclassroom.ui.mine.contract.MyClassContract;
import com.hanzhi.onlineclassroom.ui.mine.presenter.MyClassPresenter;

import java.util.List;

import butterknife.BindView;
/**
 * @class 我的课程
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class MyClassFragment extends NBaseFragment<MyClassPresenter> implements CommonTabPagerAdapter.TabPagerListener, MyClassContract.View {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.sc_vp_main)
    ViewPager mViewPager;
    private CommonTabPagerAdapter adapter;
    List<MyClassTabBean> list;
    public static MyClassFragment newInstance() {
        MyClassFragment myClassFragment = new MyClassFragment();
        Bundle args = new Bundle();
        myClassFragment.setArguments(args);
        return myClassFragment;
    }
    @Override
    protected View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_class, container, false);
        return root;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected void prepareData() {
        lifecyclePresenter.getTabList();
    }

    @Override
    public MyClassPresenter initPresenter() {
        return new MyClassPresenter(this);
    }

    @Override
    public Fragment getFragment(int position) {
        return MyClassChildFragment.newInstance(list.get(position).getId());
    }

    @Override
    public void setTabList(List<MyClassTabBean> list, List<String> titleList) {
        this.list = list;
        adapter =
                new CommonTabPagerAdapter(getChildFragmentManager(), titleList.size()
                        , titleList,this.getContext());
        adapter.setListener(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(titleList.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void refreshList() {

    }

    @Override
    public void joinClassSuccess() {

    }

    @Override
    public void joinClassTips(String message) {

    }
}
