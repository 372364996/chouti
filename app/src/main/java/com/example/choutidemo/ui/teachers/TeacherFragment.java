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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.choutidemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TeacherFragment extends Fragment implements ListViewAdapter.InnerItemOnclickListener, AdapterView.OnItemClickListener {

    private TeacherViewModel teacherViewModel;
    private ListView listView;
    private View teacherListViewHeader;
    private ListViewAdapter mAdapter;

    //private Toolbar toolbar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        teacherViewModel =
                ViewModelProviders.of(this).get(TeacherViewModel.class);
        View root = inflater.inflate(R.layout.fragment_teacher, container, false);
        listView = (ListView) root.findViewById(R.id.teacherlist);
        teacherListViewHeader = inflater.inflate(R.layout.teacherlistviewheader, null);
        getData(this, this);
        listView.addHeaderView(teacherListViewHeader);
//        listView.setOnItemClickListener(this);
//        mAdapter.setOnInnerItemOnClickListener(this);
        return root;
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("外教");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public List<Map<String, Object>> getData(final ListViewAdapter.InnerItemOnclickListener innerItemOnclickListener, final AdapterView.OnItemClickListener onItemClickListener) {
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        OkHttpClient client = new OkHttpClient();
        String path = "http://hanzhiapp.hdlebaobao.cn/teacher/GetTeacherList?userid=2";
        Request request = new Request.Builder().url(path).get().build();
        client.newCall(request).enqueue(new Callback() {
                                            //失败
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                Log.d("MainActivity", "连接失败" + e.getLocalizedMessage());
                                            }

                                            //成功
                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                final String result = response.body().string();
                                                Log.d("MainActivity", result);

                                                getActivity().runOnUiThread(
                                                        new Runnable() {
                                                            @RequiresApi(api = Build.VERSION_CODES.N)
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(result);
                                                                    JSONArray array = jsonObject.getJSONArray("data");
                                                                    for (int i = 0; i < array.length(); i++) {
                                                                        JSONObject object = (JSONObject) array.opt(i);
                                                                        Map<String, Object> map = new HashMap<String, Object>();
                                                                        // map.put("image", R.drawable.ic_launcher);
                                                                        map.put("title", object.getString("Name"));
                                                                        map.put("info", object.getString("Description"));
                                                                        map.put("headImg", object.getString("HeadImg"));
                                                                        map.put("tags", object.getString("Tags"));
                                                                        map.put("avgScore", object.getString("AvgScore"));


                                                                        list.add(map);
                                                                    }
//                                                                    list.stream().filter(x->"b".equals(x.get("tags")));
                                                                    mAdapter = new ListViewAdapter(getContext(), list);
                                                                    mAdapter.setOnInnerItemOnClickListener(innerItemOnclickListener);
                                                                    listView.setOnItemClickListener(onItemClickListener);
                                                                    listView.setAdapter(mAdapter);
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }


                                                            }
                                                        }
                                                );
                                                if (response.body() != null) {
                                                    response.body().close();
                                                }
                                            }
                                        }
        );


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

