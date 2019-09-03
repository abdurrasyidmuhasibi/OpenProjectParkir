package com.example.parkir.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.account.Assignment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPenugasan extends AppCompatActivity implements View.OnClickListener {

    Button edit;
    EditText etNama, etAlamat, etKecamatan, etKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_penugasan);

        edit = (Button) findViewById(R.id.btn_edit_penugasan);
        etNama = (EditText) findViewById(R.id.et_nama);
        etAlamat = (EditText) findViewById(R.id.et_alamat);
        etKecamatan = (EditText) findViewById(R.id.et_kecamatan);
        etKota = (EditText) findViewById(R.id.et_kota);

        edit.setOnClickListener(this);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String jwtToken = prefShared.getStr("jwtToken");

        api service = RetrofitClient.getRetrofitInstance().create(api.class);
        Call<AccountModel> call = service.account(jwtToken);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                etNama.setText("" + response.body().getData().getDatas().getAssignment().getLocationName());
                etAlamat.setText("" + response.body().getData().getDatas().getAssignment().getLocationAddress());
                etKecamatan.setText("" + response.body().getData().getDatas().getAssignment().getDistrict());
                etKota.setText("" + response.body().getData().getDatas().getAssignment().getCity());
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(EditPenugasan.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_penugasan:
                if (TextUtils.isEmpty(etNama.getText().toString().trim())) {
                    etNama.setError("Nama Lokasi diperlukan!");
                } else if (TextUtils.isEmpty(etAlamat.getText().toString().trim())) {
                    etAlamat.setError("Alamat Lokasi diperlukan!");
                } else if (TextUtils.isEmpty(etKecamatan.getText().toString().trim())) {
                    etKecamatan.setError("Kecamatan diperlukan!");
                } else if (TextUtils.isEmpty(etKota.getText().toString().trim())) {
                    etKota.setError("Kota diperlukan!");
                } else {
                    daftarPenugasan();
                    Toast.makeText(EditPenugasan.this, "Edit Penugasan berhasil!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void daftarPenugasan() {
        String namaLokasi = etNama.getText().toString().trim();
        String alamatLokasi = etAlamat.getText().toString().trim();
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
                Toast.makeText(EditPenugasan.this, "Success Daftar Penugasan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable t) {
                Toast.makeText(EditPenugasan.this, "Error, harap periksa koneksi Internet Anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
