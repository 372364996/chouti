package com.hanzhi.chouti.ui.mine.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hanzhi.chouti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyProfileFragment extends Fragment {

    private TextView userName;
    private TextView userSex;
    private TextView level;
    public static MyProfileFragment newInstance() {
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        Bundle args = new Bundle();
        myProfileFragment.setArguments(args);
        return myProfileFragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        userName = root.findViewById(R.id.userName);
        userSex = root.findViewById(R.id.userSex);
        level = root.findViewById(R.id.level);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    public void init() throws IOException {
        String path = "http://hanzhiapp.hdlebaobao.cn/user/GetUserById?userId=2";
        OkHttpClient client = new OkHttpClient();
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
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(result);
                                                                    if (jsonObject.getString("success") == "true") {
                                                                        userName.setText(jsonObject.getString("NickName"));
                                                                        userSex.setText(String.valueOf(jsonObject.getString("Sex")));
                                                                        level.setText(String.valueOf(jsonObject.getString("Level")));
                                                                    } else {
                                                                        Toast.makeText(getActivity(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                                                                    }
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

    }

}
