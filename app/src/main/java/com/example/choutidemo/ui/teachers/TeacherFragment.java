package com.example.choutidemo.ui.teachers;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.choutidemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherFragment extends Fragment implements ListViewAdapter.InnerItemOnclickListener, AdapterView.OnItemClickListener {

    private TeacherViewModel teacherViewModel;
    private ListView listView;
    private  View  teacherListViewHeader;
    private ListViewAdapter mAdapter;
    //private Toolbar toolbar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        teacherViewModel =
                ViewModelProviders.of(this).get(TeacherViewModel.class);
        View root = inflater.inflate(R.layout.fragment_teacher, container, false);
        listView = (ListView) root.findViewById(R.id.teacherlist);
       teacherListViewHeader= inflater.inflate(R.layout.teacherlistviewheader, null);
        List<Map<String, Object>> list = getData();

        mAdapter = new ListViewAdapter(getContext(), list);
        listView.setAdapter(mAdapter);
        listView.addHeaderView(teacherListViewHeader);
        listView.setOnItemClickListener(this);
        mAdapter.setOnInnerItemOnClickListener(this);
        return root;
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("外教");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            // map.put("image", R.drawable.ic_launcher);
            map.put("title", "这是一个标题" + i);
            map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;
    }

    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.zan:
                Log.e("内部item--1-->", position + "");
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("整体item----->", position + "");
    }
}

