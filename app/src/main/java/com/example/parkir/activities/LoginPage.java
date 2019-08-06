package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.model.login.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    private EditText etUsername, etPassword;
    api mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void userLogin(){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        mApiInterface = RetrofitClient.getRetrofitInstance().create(api.class);

        Call<LoginModel> postLoginCall = mApiInterface.logins(username, password);
        postLoginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginPage.this,"Success, Selamat datang "+ response.body().getData().getDatas().getAccountData().getFullName(),Toast.LENGTH_LONG).show();

                    Intent i = new Intent(LoginPage.this,HomeUser.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginPage.this,"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginPage.this,"Error failure",Toast.LENGTH_LONG).show();
            }
        });
    }
}
