package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.PaymentParking.PaymentParkingModel;
import com.example.parkir.model.notification.NotificationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembayaran extends AppCompatActivity {

    TextView txtName, txtLocationName, txtLocationAddress, txtNominal, txtAccountId, txtParkingTypeId, txtFcmToken;
    EditText etPlatNomor;
    private Spinner spNamen2;
    private String[] parkingType = {
            "Pilih Type Parkir",
            "Motor",
            "Mobil",
            "Truk/Minibus",
            "Truk Gandeng/Bus Besar"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);
        txtParkingTypeId = (TextView) findViewById(R.id.txtParkingTypeId);

        spNamen2 = (Spinner) findViewById(R.id.sp_name_2);
        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, parkingType);

        // mengeset Array Adapter tersebut ke Spinner
        spNamen2.setAdapter(adapter);

        // mengeset listener untuk mengetahui saat item dipilih
        spNamen2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String parkingType = "1";
                String nominal = "2000";
                if (adapter.getItem(i).equals("Pilih Type Parkir")){
                    parkingType = "0";
                    nominal = "0";
                }else if (adapter.getItem(i).equals("Motor")){
                    parkingType = "1";
                    nominal = "2000";
                }else if (adapter.getItem(i).equals("Mobil")){
                    parkingType = "2";
                    nominal = "3000";
                }else if (adapter.getItem(i).equals("Truk/Minibus")){
                    parkingType = "3";
                    nominal = "5000";
                }else if (adapter.getItem(i).equals("Truk Gandeng/Bus Besar")){
                    parkingType = "4";
                    nominal = "10000";
                }else{
                    parkingType = "1";
                    nominal = "2000";
                }

                txtParkingTypeId.setText(parkingType);
                txtNominal.setText("Rp. "+ nominal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etPlatNomor = (EditText) findViewById(R.id.etPlatNomor);
        txtFcmToken = (TextView) findViewById(R.id.txtFcmToken);
        txtName = (TextView) findViewById(R.id.txtName);
        txtLocationName = (TextView) findViewById(R.id.txtLocationName);
        txtLocationAddress = (TextView) findViewById(R.id.txtLocationAddress);
        txtNominal = (TextView) findViewById(R.id.txtNominal);
        txtAccountId = (TextView) findViewById(R.id.txtAccountId);
        txtAccountId.setVisibility(View.INVISIBLE);
        txtParkingTypeId.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        String fcmToken = intent.getStringExtra("fcmToken");
        String accountid = intent.getStringExtra("accountid");
        String name = intent.getStringExtra("name");
        String location_name = intent.getStringExtra("location_name");
        String location_address = intent.getStringExtra("location_address");
        String nominal = intent.getStringExtra("nominal");

        txtFcmToken.setText(fcmToken);
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
        String parkingtypeid = txtParkingTypeId.getText().toString();
        String platNomor = etPlatNomor.getText().toString();

        if (parkingtypeid.equals("0")){
            Toast.makeText(DetailPembayaran.this, "Type parkir belum terisi.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(etPlatNomor.getText().toString())){
            Toast.makeText(DetailPembayaran.this, "Plat nomor belum terisi.", Toast.LENGTH_SHORT).show();
        }else{
            /*Create handle for the RetrofitInstance interface*/
            api service = RetrofitClient.getRetrofitInstance().create(api.class);
            Call<PaymentParkingModel> call = service.payments(jwtToken, receiverid, parkingtypeid, platNomor);
            call.enqueue(new Callback<PaymentParkingModel>() {
                @Override
                public void onResponse(Call<PaymentParkingModel> call, Response<PaymentParkingModel> response) {
                    Toast.makeText(DetailPembayaran.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                    /* SEND NOTIF */
                    String fcmToken = txtFcmToken.getText().toString();
                    String nominal = txtNominal.getText().toString();
                    String platNomor = etPlatNomor.getText().toString();
                    api service = RetrofitClient.getRetrofitInstance().create(api.class);
                    Call<NotificationModel> call2 = service.notifications(fcmToken ,nominal, platNomor);
                    call2.enqueue(new Callback<NotificationModel>() {
                        @Override
                        public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                            Toast.makeText(DetailPembayaran.this, ""+ response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailPembayaran.this, Success.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<NotificationModel> call, Throwable t) {
                            Toast.makeText(DetailPembayaran.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<PaymentParkingModel> call, Throwable t) {
                    Toast.makeText(DetailPembayaran.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
