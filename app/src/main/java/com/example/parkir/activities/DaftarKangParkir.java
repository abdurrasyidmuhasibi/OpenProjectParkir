package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.daftar.RegisterModel;
import com.example.parkir.model.login.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKangParkir extends AppCompatActivity implements View.OnClickListener {

    int roleid = 1;
    EditText etNama, etEmail, etAlamat, etUsername, etPassword;
    CheckBox showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kang_parkir);

        etNama = (EditText) findViewById(R.id.et_nama);
        etEmail = (EditText) findViewById(R.id.et_email);
        etAlamat = (EditText) findViewById(R.id.et_alamat);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        showPass = (CheckBox) findViewById(R.id.showPass);

        findViewById(R.id.btn_daftar).setOnClickListener(this);
        showPass.setOnClickListener(this);
    }

    private void daftarUser() {
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        Call<RegisterModel> call = RetrofitClient
                .getRetrofitInstance()
                .create(api.class)
                .register(roleid, username, password, nama, email, alamat);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                Toast.makeText(DaftarKangParkir.this, "Success Daftar KangParkir", Toast.LENGTH_SHORT).show();
                Call<LoginModel> login = RetrofitClient
                        .getRetrofitInstance()
                        .create(api.class)
                        .logins(username, password);
                login.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        Toast.makeText(DaftarKangParkir.this, "Berhasil Login, Lanjut Daftar Penugasan", Toast.LENGTH_SHORT).show();
                        processSaveToken(response.body().getData().getDatas().getJwtTokenData(), response.body().getData().getDatas().getAccountData().getAccountRole().getId().toString(), response.body().getData().getDatas().getAccountData().getId().toString());
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        Toast.makeText(DaftarKangParkir.this, "Gabisa Lanjut ke Penugasan", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(DaftarKangParkir.this, "Error, harap periksa koneksi Internet Anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_daftar:
                if (TextUtils.isEmpty(etNama.getText().toString().trim())) {
                    etNama.setError("Nama diperlukan!");
                } else if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
                    etEmail.setError("Email diperlukan!");
                } else if (!isValidEmail(etEmail.getText().toString().trim())) {
                    etEmail.setError("Email tidak valid!");
                } else if (TextUtils.isEmpty(etAlamat.getText().toString().trim())) {
                    etAlamat.setError("Alamat diperlukan!");
                } else if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
                    etUsername.setError("Usernam diperlukan!");
                } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                    etPassword.setError("Password diperlukan!");
                } else {
                    daftarUser();
                    Intent i = new Intent(DaftarKangParkir.this, DaftarPenugasan.class);
                    startActivity(i);
                }
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

    private void processSaveToken(String jwtToken, String roleid, String accountid) {
        PreferenceHelper prefShared = new PreferenceHelper(this);
        prefShared.setStr("jwtToken", "Bearer " + jwtToken);
        prefShared.setStr("roleid", roleid);
        prefShared.setStr("accountid", accountid);
        if (roleid.equals("1")) {
            Intent i = new Intent(DaftarKangParkir.this, DaftarPenugasan.class);
            startActivity(i);
        } else {
            Intent i = new Intent(DaftarKangParkir.this, HomeUser.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public static boolean isValidEmail(CharSequence email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
