package com.hanzhi.chouti.ui.teachers.fragment;

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
import com.hanzhi.chouti.ui.mine.fragment.MyClassChildFragment;
import com.hanzhi.chouti.ui.mine.presenter.MyClassPresenter;

import java.util.List;

import butterknife.BindView;
/**
 * @class 老师获取报名自己的课程
 * @anthor dupeng email:372364996@qq.com
 * @time 2021年3月18日 23点10分
 */
public class TeacherOrderFragment extends NBaseFragment<MyClassPresenter> implements CommonTabPagerAdapter.TabPagerListener, MyClassContract.View {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.sc_vp_main)
    ViewPager mViewPager;
    private CommonTabPagerAdapter adapter;
    List<MyClassTabBean> list;
    public static TeacherOrderFragment newInstance() {
        TeacherOrderFragment myClassFragment = new TeacherOrderFragment();
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
