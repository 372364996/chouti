package com.example.choutidemo.ui.yuyue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.choutidemo.ui.selectclass.SelectClassItemFragment;

class YuYueFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"周一", "周二","周三","周四", "周五", "周六", "周日"};
    private  Integer id;
    public YuYueFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return new YuYueItemFragment(position);
    }
    @Override
    public int getCount() {
        return mTitles.length;
    }
    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}