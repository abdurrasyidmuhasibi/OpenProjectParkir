package com.example.parkir.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.model.daftar.RegisterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarUser extends AppCompatActivity implements View.OnClickListener {

    int roleid = 2;
    EditText etNama, etEmail, etAlamat, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_user);

        etNama = (EditText) findViewById(R.id.et_namaLokasi);
        etEmail = (EditText) findViewById(R.id.et_alamatLokasi);
        etAlamat = (EditText) findViewById(R.id.et_kecamatan);
        etUsername = (EditText) findViewById(R.id.et_kota);
        etPassword = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.btn_daftar).setOnClickListener(this);
    }

    private void daftarUser(){
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Call<RegisterModel> call = RetrofitClient
                .getRetrofitInstance()
                .create(api.class)
                .register(roleid,username,password,nama,email,alamat);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                Toast.makeText(DaftarUser.this,"Success Daftar User",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(DaftarUser.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_daftar:
                daftarUser();
                break;
        }
    }
}
