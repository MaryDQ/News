package com.mlxphone.news.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mlxphone.news.R;
import com.mlxphone.news.entity.RegisterInfo;
import com.mlxphone.news.utils.MD5Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.et_user_account)
    EditText et_user_account;
    @InjectView(R.id.et_user_password)
    EditText et_user_password;
    @InjectView(R.id.et_user_email)
    EditText et_user_email;
    String user_account,user_password,user_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.btn_register,R.id.btn_login})
    public void Click(View v){
        user_account=et_user_account.getText().toString();
        user_password=et_user_password.getText().toString();
        user_email=et_user_email.getText().toString();
        switch (v.getId()){
            case R.id.btn_register:
                if (user_account.equals("")||user_password.equals("")||user_email.equals("")){
                    Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                }else{
                    register(user_account,user_password,user_email);
                }
                break;
            case R.id.btn_login:
                if (user_account.equals("")||user_password.equals("")||!user_email.equals("")){
                    Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                }else{
                    login(user_account,user_password);
                }
                break;
        }
    }

    private void login(String user_account, String user_password) {
        user_password= MD5Util.getMD5(user_password);
        String url="http://118.244.212.82:9092/newsClient/user_login?uid="+user_account+"&pwd="+user_password+"&imei=abc&ver=1&device=1";
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                RegisterInfo registerInfo=gson.fromJson(response,RegisterInfo.class);
                if (registerInfo.getStatus()==0){
                    Toast.makeText(LoginActivity.this,registerInfo.getData().getExplain() , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "登录失败请检查账号密码", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    private void register(String user_account, String user_password, String user_email) {
        user_password= MD5Util.getMD5(user_password);
        String url="http://118.244.212.82:9092/newsClient/user_register?ver=1&uid="+user_account+"&pwd="+user_password+"&email="+user_email;
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                RegisterInfo registerInfo=gson.fromJson(response,RegisterInfo.class);
                if (registerInfo.getStatus()==0){
                    Toast.makeText(LoginActivity.this,registerInfo.getData().getExplain() , Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "注册失败请检查", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

}
