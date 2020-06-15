package com.example.choutidemo.ui.yuyue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.choutidemo.ui.selectclass.SelectClassItemFragment;

class YuYueFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"体验课", "初级","中级","中高级", "高级", "发音"};
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