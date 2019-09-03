package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.account.Assignment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPenugasan extends AppCompatActivity implements View.OnClickListener {

    CheckBox cbSyarat;
    EditText etNamaLokasi, etAlamatLokasi, etKecamatan, etKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penugasan);

        cbSyarat = (CheckBox) findViewById(R.id.cb_syarat);
        etNamaLokasi = (EditText) findViewById(R.id.et_namaLokasi);
        etAlamatLokasi = (EditText) findViewById(R.id.et_alamatLokasi);
        etKecamatan = (EditText) findViewById(R.id.et_kecamatan);
        etKota = (EditText) findViewById(R.id.et_kota);

        findViewById(R.id.btn_daftar).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_daftar:
                if (TextUtils.isEmpty(etNamaLokasi.getText().toString().trim())) {
                    etNamaLokasi.setError("Nama Lokasi diperlukan!");
                } else if (TextUtils.isEmpty(etAlamatLokasi.getText().toString().trim())) {
                    etAlamatLokasi.setError("Alamat Lokasi diperlukan!");
                } else if (TextUtils.isEmpty(etKecamatan.getText().toString().trim())) {
                    etKecamatan.setError("Kecamatan diperlukan!");
                } else if (TextUtils.isEmpty(etKota.getText().toString().trim())) {
                    etKota.setError("Kota diperlukan!");
                } else if (cbSyarat.isChecked()) {
                    daftarPenugasan();
                    Intent i = new Intent(DaftarPenugasan.this, HomeKangParkir.class);
                    startActivity(i);
                } else {
                    Toast.makeText(DaftarPenugasan.this, "Centang Syarat & Ketentuan untuk melanjutkan Pendaftaran", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void daftarPenugasan() {
        String namaLokasi = etNamaLokasi.getText().toString().trim();
        String alamatLokasi = etAlamatLokasi.getText().toString().trim();
        String kecamatan = etKecamatan.getText().toString().trim();
        String kota = etKota.getText().toString().trim();

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String token = prefShared.getStr("jwtToken");

        Call<Assignment> call = RetrofitClient
                .getRetrofitInstance()
                .create(api.class)
                .assignment(token, namaLokasi, alamatLokasi, kecamatan, kota);

        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                Toast.makeText(DaftarPenugasan.this, "Success Daftar Penugasan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable t) {
                Toast.makeText(DaftarPenugasan.this, "Error, harap periksa koneksi Internet Anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), DaftarKangParkir.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
