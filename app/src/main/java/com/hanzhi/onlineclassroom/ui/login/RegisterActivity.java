package com.hanzhi.onlineclassroom.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanzhi.onlineclassroom.R;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button returnLoginButton = findViewById(R.id.returnlogin);
        final Button btnRegisterButton = findViewById(R.id.btnRegister);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText confirmpasswordEditText = findViewById(R.id.confirmpassword);
        final CheckBox serviceAgreementCheckBox = findViewById(R.id.cb_confirm);

        returnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "实现点击TextView事件", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterSubmit();
            }

            public void RegisterSubmit() {
                if (!serviceAgreementCheckBox.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "请阅读用户服务协议并勾选，再进行注册操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = ProgressDialog.show(RegisterActivity.this, "", "正在注册...");
                final String name = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String confirmpassword = confirmpasswordEditText.getText().toString();
                if (!password.equals(confirmpassword)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }
                String url = "http://hanzhiapp.hdlebaobao.cn/home/AppRegister";
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("phone", name);
                map.put("password", password);
                httpPost(url, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //异常
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //此处为正常返回进行的操作
                                String responseData = null; //提前定义全局变量 String类型
                                try {
                                    responseData = response.body().string();
                                    dialog.dismiss();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //接下来可对获得的数据进行解析或其他处理

                                JSONObject jsonObj = (JSONObject) JSON.parse(responseData);

                                boolean success = jsonObj.getBoolean("success");
                                String msg = jsonObj.getString("msg");
                                if (success) {
                                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();

                                }

                            }
                        });


                    }
                });

            }

            public void httpPost(final String url, final Map<String, String> params, final Callback callback) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        FormBody.Builder formBodyBuilder = new FormBody.Builder();
                        Set<String> keySet = params.keySet();
                        for (String key : keySet) {
                            String value = params.get(key);
                            formBodyBuilder.add(key, value);
                        }
                        FormBody formBody = formBodyBuilder.build();
                        Request request = new Request
                                .Builder()
                                .post(formBody)
                                .url(url)
                                .build();
                        //Response response = null;
                        okHttpClient.newCall(request).enqueue(callback);
                    }
                }).start();
            }
        });
    }

}
