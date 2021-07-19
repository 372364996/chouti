package com.hanzhi.onlineclassroom.ui.appointment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chewawa.baselibrary.base.NBaseFragment;
import com.chewawa.baselibrary.view.viewpager.CommonTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.ClassApplyBean;
import com.hanzhi.onlineclassroom.bean.appointment.AppointmentTabBean;
import com.hanzhi.onlineclassroom.ui.appointment.contract.AppointmentTimeContract;
import com.hanzhi.onlineclassroom.ui.appointment.contract.SetAppointmentTimeContract;
import com.hanzhi.onlineclassroom.ui.appointment.presenter.AppointmentTimePresenter;
import com.hanzhi.onlineclassroom.ui.appointment.presenter.SetAppointmentTimePresenter;
import com.hanzhi.onlineclassroom.view.ColorTrackTabLayout;

import java.util.List;

import butterknife.BindView;

public class SetAppointmentTimeFragment extends NBaseFragment<SetAppointmentTimePresenter> implements SetAppointmentTimeContract.View, CommonTabPagerAdapter.TabPagerListener {

    @BindView(R.id.tabs)
    ColorTrackTabLayout tabLayout;
    @BindView(R.id.yy_vp_main)
    ViewPager mViewPager;
    private CommonTabPagerAdapter adapter;
    List<AppointmentTabBean> list;
    ClassApplyBean classApplyBean;
    public static SetAppointmentTimeFragment newInstance(ClassApplyBean classApplyBean) {
        SetAppointmentTimeFragment appointmentTimeFragment = new SetAppointmentTimeFragment();
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
    public SetAppointmentTimePresenter initPresenter() {
        return new SetAppointmentTimePresenter(this);
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
        for (int i = 0; i < titleList.size(); i++) {
            setTabItem(i, titleList);
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
    public void setTabItem(int position, List<String> titleList) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_week, null);
        TextView textView = view.findViewById(R.id.tab_name);
        textView.setText(titleList.get(position));
        tabLayout.getTabAt(position).setCustomView(view);

    }
    @Override
    public Fragment getFragment(int position) {
        if(list == null){
            return SetAppointmentTimeChildFragment.newInstance("", classApplyBean);
        }
        return SetAppointmentTimeChildFragment.newInstance(list.get(position).getDate(), classApplyBean);
    }
}
