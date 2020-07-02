package com.example.choutidemo.ui.selectclass;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.choutidemo.R;
import com.example.choutidemo.ui.teachers.ListViewAdapter;
import com.google.android.material.tabs.TabLayout;

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

public class SelectClassItemFragment extends Fragment {
    //    ArrayList<String> strList = new ArrayList<>();
    private GridViewSelectClassAdapter mAdapter;
    GridView grid_view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Integer id;
    private List<Map<String, Object>> list;

    public SelectClassItemFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selectclass_item, container, false);
        //list.clear();


        grid_view = root.findViewById(R.id.grid_view);
        getData();
        return root;
    }

    public List<Map<String, Object>> getData() {
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        OkHttpClient client = new OkHttpClient();
        String path = "http://hanzhiapp.hdlebaobao.cn/class/GetClassList?size=200";
        Request request = new Request.Builder().url(path).get().build();
        client.newCall(request).enqueue(new Callback() {
                                            //失败
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                Log.e("selectclassitem", "连接失败" + e.getLocalizedMessage());
                                            }

                                            //成功
                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                final String result = response.body().string();
                                                Log.e("selectclassitem", result);
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(result);
                                                            JSONArray array = jsonObject.getJSONArray("classList");
                                                            for (int i = 0; i < array.length(); i++) {
                                                                JSONObject object = (JSONObject) array.opt(i);
                                                                Map<String, Object> map = new HashMap<String, Object>();
                                                                // map.put("image", R.drawable.ic_launcher);
                                                                map.put("Name", object.getString("Name"));
                                                                map.put("Img", "http://hanzhimgr.hdlebaobao.cn" + object.getString("Img"));
                                                                map.put("TypeId", object.getString("ClassType"));
                                                                //Log.e("课程类型ID"+object.getString("ClassType"),"ViewPagerId"+id);
                                                                if (object.getInt("ClassType")-1 == id) {
                                                                    list.add(map);
                                                                }

                                                            }
                                                            mAdapter = new GridViewSelectClassAdapter(getContext(), list);
                                                            grid_view.setAdapter(mAdapter);

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                });

                                                if (response.body() != null) {
                                                    response.body().close();
                                                }
                                            }
                                        }
        );


        return list;
    }

}
