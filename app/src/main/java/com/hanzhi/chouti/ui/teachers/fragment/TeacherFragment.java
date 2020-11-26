package com.hanzhi.chouti.ui.teachers.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.teachers.TeacherBean;
import com.hanzhi.chouti.network.Constants;
import com.hanzhi.chouti.ui.teachers.TeacherInfo;
import com.hanzhi.chouti.ui.teachers.adapter.TeacherAdapter;

import java.util.Map;

public class TeacherFragment extends BaseRecycleViewFragment<TeacherBean>  {

    @Override
    protected String getUrl() {
        return Constants.GET_TEACHER_LIST_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.put("userid", 2);
        return params;
    }

    @Override
    protected Class<TeacherBean> getResultClass() {
        return TeacherBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter getAdapter() {
        return new TeacherAdapter();
    }

    @Override
    protected View addHeaderView() {
        headerView = inflater.inflate(R.layout.teacherlistviewheader, null);
        return headerView;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        Log.e("整体item----->", position + "");
        Intent intent = new Intent(getContext(), TeacherInfo.class);
        super.startActivity(intent);
    }
}

