package com.example.choutidemo.ui.yuyue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.choutidemo.R;
import com.example.choutidemo.ui.selectclass.GridViewSelectClassAdapter;
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

public class YuYueItemFragment extends Fragment {
    //    ArrayList<String> strList = new ArrayList<>();
    private GridViewYuYueAdapter mAdapter;
    GridView grid_view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Integer id;
    private List<Map<String, Object>> list;
//    String list1[] = {"00:00"
//            , "00:30"
//            , "01:00"
//            , "01:30"
//            , "02:00"
//            , "02:30"
//            , "03:00"
//            , "03:30"
//            , "04:00"
//            , "04:30"
//            , "05:00"
//            , "05:30"
//            , "06:00"
//            , "06:30"
//            , "07:00"
//            , "07:30"
//            , "08:00"
//            , "08:30"
//            , "09:00"
//            , "09:30"
//            , "10:00"
//            , "10:30"
//            , "11:00"
//            , "11:30"
//            , "12:00"
//            , "12:30"
//            , "13:00"
//            , "13:30"
//            , "14:00"
//            , "14:30"
//            , "15:00"
//            , "15:30"
//            , "16:00"
//            , "16:30"
//            , "17:00"
//            , "17:30"
//            , "18:00"
//            , "18:30"
//            , "19:00"
//            , "19:30"
//            , "20:00"
//            , "20:30"
//            , "21:00"
//            , "21:30"
//            , "22:00"
//            , "22:30"
//            , "23:00"
//            , "23:30"};

    public YuYueItemFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_yuyue_item, container, false);
        //list.clear();


        grid_view = root.findViewById(R.id.grid_view);
        getData();
        return root;
    }

    public List<Map<String, Object>> getData() {
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(new HashMap<String, Object>() {{
            put("Name", "00:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "00:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "01:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "01:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "02:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "02:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "03:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "03:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "04:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "04:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "05:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "05:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "06:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "06:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "07:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "07:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "08:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "08:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "09:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "09:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "10:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "10:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "11:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "11:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "12:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "12:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "13:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "13:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "14:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "14:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "15:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "15:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "16:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "16:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "17:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "17:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "18:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "18:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "19:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "19:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "20:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "20:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "21:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "21:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "22:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "22:30");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "23:00");
        }});
        list.add(new HashMap<String, Object>() {{
            put("Name", "23:30");
        }});
        mAdapter = new GridViewYuYueAdapter(getContext(), list);
        grid_view.setAdapter(mAdapter);
//        OkHttpClient client = new OkHttpClient();
//        String path = "http://hanzhiapp.hdlebaobao.cn/class/GetClassList?size=200";
//        Request request = new Request.Builder().url(path).get().build();
//        client.newCall(request).enqueue(new Callback() {
//                                            //失败
//                                            @Override
//                                            public void onFailure(Call call, IOException e) {
//                                                Log.e("selectclassitem", "连接失败" + e.getLocalizedMessage());
//                                            }
//
//                                            //成功
//                                            @Override
//                                            public void onResponse(Call call, Response response) throws IOException {
//                                                final String result = response.body().string();
//                                                Log.e("selectclassitem", result);
//                                                getActivity().runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        try {
//                                                            JSONObject jsonObject = new JSONObject(result);
//                                                            JSONArray array = jsonObject.getJSONArray("classList");
//                                                            for (int i = 0; i < array.length(); i++) {
//                                                                JSONObject object = (JSONObject) array.opt(i);
//
//
////                                                                map.put("Img", "http://hanzhimgr.hdlebaobao.cn" + object.getString("Img"));
////                                                                map.put("TypeId", object.getString("ClassType"));
////                                                                Log.e("课程类型ID"+object.getString("ClassType"),"ViewPagerId"+id);
////                                                                if (object.getInt("ClassType")-1 == id) {
////
////                                                                }
//
//                                                            }
//                                                            mAdapter = new GridViewYuYueAdapter(getContext(), list);
//                                                            grid_view.setAdapter(mAdapter);
//
//                                                        } catch (JSONException e) {
//                                                            e.printStackTrace();
//                                                        }
//
//                                                    }
//                                                });
//
//                                                if (response.body() != null) {
//                                                    response.body().close();
//                                                }
//                                            }
//                                        }
        //);


        return list;
    }

}
