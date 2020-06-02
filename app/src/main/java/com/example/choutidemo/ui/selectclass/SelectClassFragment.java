package com.example.choutidemo.ui.selectclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.choutidemo.R;
import com.google.android.material.tabs.TabLayout;

public class SelectClassFragment extends Fragment {

    private SelectClassViewModel selectClassViewModel;
    private GridView gridView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selectclass, container, false);
        mTabLayout = root.findViewById(R.id.tabs);
        mViewPager = root.findViewById(R.id.vp_main);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout和ViewPager绑定在一起，相互影响，解放了开发人员对双方变动事件的监听
        mTabLayout.setupWithViewPager(mViewPager);
        return root;
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("选择课程");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

}
