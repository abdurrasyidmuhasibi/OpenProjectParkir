package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.login.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername, etPassword;
    private CheckBox showPass;
    api mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        autoLogin();
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        showPass = (CheckBox) findViewById(R.id.showPass);

        findViewById(R.id.btn_login).setOnClickListener(this);
        showPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                userLogin();
                break;
            case R.id.showPass:
                if (showPass.isChecked()) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }

    private void userLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        mApiInterface = RetrofitClient.getRetrofitInstance().create(api.class);

        Call<LoginModel> postLoginCall = mApiInterface.logins(username, password);
        postLoginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginPage.this, "Success, Selamat datang " + response.body().getData().getDatas().getAccountData().getFullName(), Toast.LENGTH_LONG).show();
                    processSaveToken(response.body().getData().getDatas().getJwtTokenData(), response.body().getData().getDatas().getAccountData().getAccountRole().getId().toString(), response.body().getData().getDatas().getAccountData().getId().toString());
                } else {
                    Toast.makeText(LoginPage.this, "Username/Password Salah!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Error, harap periksa koneksi Internet Anda!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void processSaveToken(String jwtToken, String roleid, String accountid) {
        PreferenceHelper prefShared = new PreferenceHelper(this);
        prefShared.setStr("jwtToken", "Bearer " + jwtToken);
        prefShared.setStr("roleid", roleid);
        prefShared.setStr("accountid", accountid);
        if (roleid.equals("1")) {
            Intent i = new Intent(LoginPage.this, HomeKangParkir.class);
            startActivity(i);
        } else {
            Intent i = new Intent(LoginPage.this, HomeUser.class);
            startActivity(i);
        }
    }

    private void autoLogin() {
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String jwtToken = prefShared.getStr("jwtToken");
        String accountid = prefShared.getStr("accountid");
        String roleid = prefShared.getStr("roleid");

        if (jwtToken != null) {
            if (accountid != null) {
                if (roleid.equals("1")) {
                    Intent i = new Intent(LoginPage.this, HomeKangParkir.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(LoginPage.this, HomeUser.class);
                    startActivity(i);
                }
            } else {
                Toast.makeText(LoginPage.this, "Silahkan re-login terlebih dahulu.", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(LoginPage.this, "Silahkan login terlebih dahulu.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
