package com.example.parkir.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.account.Datas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfil extends AppCompatActivity implements View.OnClickListener {

    Button edit;
    EditText etNama, etEmail, etAlamat, etUsername, etPassword;
    CheckBox showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        edit = (Button) findViewById(R.id.btn_edit_profil);
        etNama = (EditText) findViewById(R.id.et_nama);
        etEmail = (EditText) findViewById(R.id.et_email);
        etAlamat = (EditText) findViewById(R.id.et_alamat);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        showPass = (CheckBox) findViewById(R.id.showPass);

        showPass.setOnClickListener(this);
        edit.setOnClickListener(this);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String jwtToken = prefShared.getStr("jwtToken");

        api service = RetrofitClient.getRetrofitInstance().create(api.class);
        Call<AccountModel> call = service.account(jwtToken);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                etNama.setText("" + response.body().getData().getDatas().getFullName());
                etEmail.setText("" + response.body().getData().getDatas().getEmail());
                etAlamat.setText("" + response.body().getData().getDatas().getAddress());
                etUsername.setText("" + response.body().getData().getDatas().getUsername());
                etPassword.setText("" + response.body().getData().getDatas().getPassword());
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(EditProfil.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_profil:
                if (TextUtils.isEmpty(etNama.getText().toString().trim())) {
                    etNama.setError("Nama diperlukan!");
                } else if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
                    etEmail.setError("Email diperlukan!");
                } else if (TextUtils.isEmpty(etAlamat.getText().toString().trim())) {
                    etAlamat.setError("Alamat diperlukan!");
                } else if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
                    etUsername.setError("Username diperlukan!");
                } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                    etPassword.setError("Password diperlukan!");
                } else {
                    editProfil();
                    Toast.makeText(EditProfil.this, "Edit Profil berhasil!", Toast.LENGTH_SHORT).show();
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

    private void editProfil() {
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String token = prefShared.getStr("jwtToken");

        Call<Datas> call = RetrofitClient
                .getRetrofitInstance()
                .create(api.class)
                .profil(token, nama, email, alamat, username, password);

        call.enqueue(new Callback<Datas>() {
            @Override
            public void onResponse(Call<Datas> call, Response<Datas> response) {
                Toast.makeText(EditProfil.this, "Success Edit Profil", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Datas> call, Throwable t) {
                Toast.makeText(EditProfil.this, "Error, harap periksa koneksi Internet Anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
