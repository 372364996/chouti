package com.example.choutidemo.ui.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.choutidemo.MainActivity;
import com.example.choutidemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView registerTextView = findViewById(R.id.register);
        final TextView forgetpasswordTextView = findViewById(R.id.forgetpassword);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forgetpasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "忘记密码?", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                finish();
//                dialog = ProgressDialog.show(LoginActivity.this, "", "弹框...");
//                dialog.dismiss();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                try {
                    LoginGet();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            public void LoginGet() throws IOException {
                dialog = ProgressDialog.show(LoginActivity.this, "", "正在登录...");
                final String name = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                String path = "http://hanzhiapp.hdlebaobao.cn/home/applogin?phone=" + name + "&password=" + password;
                System.out.println(path);
                System.out.println("用户名：" + name);
                System.out.println("密码：" + password);
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
                                                        dialog.dismiss();
                                                        runOnUiThread(
                                                                new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        if (result.contains("true")) {
                                                                            //登录成功后将用户名密码保存到SharedPreferences中
                                                                            SharedPreferences.Editor editor = getSharedPreferences("LoginInfo", MODE_PRIVATE).edit();
                                                                            editor.putString("userName", name);
                                                                            editor.putString("password", password);
                                                                            editor.apply();
                                                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        } else {
                                                                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
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

            //Post请求
            public void LoginPost(View view) {
                String name = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String url = "http://localhost:9693/home/applogin";

                OkHttpClient client = new OkHttpClient();
                //构建表单参数
                FormBody.Builder requestBuild = new FormBody.Builder();
                //添加请求体
                RequestBody requestBody = requestBuild
                        .add("username", name)
                        .add("password", password)
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                System.out.println(request.toString());
                //异步请求
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("MainActivityPost", "连接失败" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.d("MainActivity", result);
                        if (response.body() != null) {
                            response.body().close();
                        }
                    }

                });
            }
        });
    }
}
