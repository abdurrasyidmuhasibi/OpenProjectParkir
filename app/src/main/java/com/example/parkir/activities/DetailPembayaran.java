package com.example.parkir.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.PaymentParking.PaymentParkingModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembayaran extends AppCompatActivity {

    TextView txtName, txtLocationName, txtLocationAddress, txtNominal, txtAccountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);

        txtName = (TextView) findViewById(R.id.txtName);
        txtLocationName = (TextView) findViewById(R.id.txtLocationName);
        txtLocationAddress = (TextView) findViewById(R.id.txtLocationAddress);
        txtNominal = (TextView) findViewById(R.id.txtNominal);
        txtAccountId = (TextView) findViewById(R.id.txtAccountId);
        txtAccountId.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        String accountid = intent.getStringExtra("accountid");
        String name = intent.getStringExtra("name");
        String location_name = intent.getStringExtra("location_name");
        String location_address = intent.getStringExtra("location_address");
        String nominal = intent.getStringExtra("nominal");

        txtName.setText(name);
        txtLocationName.setText(location_name);
        txtLocationAddress.setText(location_address);
        txtNominal.setText(nominal);
        txtAccountId.setText(accountid);
    }

    public void clickConfirm(View view) {
        /* GET JWT TOKEN */
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String jwtToken = prefShared.getStr("jwtToken");
        /* GET JWT TOKEN */

        String receiverid = txtAccountId.getText().toString();

        /*Create handle for the RetrofitInstance interface*/
        api service = RetrofitClient.getRetrofitInstance().create(api.class);
        Call<PaymentParkingModel> call = service.payments(jwtToken, receiverid);
        call.enqueue(new Callback<PaymentParkingModel>() {
            @Override
            public void onResponse(Call<PaymentParkingModel> call, Response<PaymentParkingModel> response) {
                Toast.makeText(DetailPembayaran.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailPembayaran.this, HomeUser.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PaymentParkingModel> call, Throwable t) {
                Toast.makeText(DetailPembayaran.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
