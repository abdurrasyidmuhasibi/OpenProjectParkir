package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.PaymentParking.PaymentParkingModel;
import com.example.parkir.model.account.AccountModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUser extends AppCompatActivity {

    private Button btnScan;
    private IntentIntegrator intentIntegrator;

    api mApiInterface;
    TextView txtBalance, txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        txtBalance = (TextView) findViewById(R.id.txtBalance);
        txtName = (TextView) findViewById(R.id.txtName);

        /* GET JWT TOKEN */
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String jwtToken = prefShared.getStr("jwtToken");
        /* GET JWT TOKEN */

        /*Create handle for the RetrofitInstance interface*/
        api service = RetrofitClient.getRetrofitInstance().create(api.class);
        Call<AccountModel> call = service.account(jwtToken);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                txtBalance.setText("Rp. "+ response.body().getData().getDatas().getBalance());
                txtName.setText(""+ response.body().getData().getDatas().getFullName());
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(HomeUser.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }


        });
        /* END LOAD CONTENT HOME */
    }

    public void myScan(View view){
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    public void myAkun(View view){
        Intent i = new Intent(HomeUser.this, SettingAkun.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                /* GET JWT TOKEN */
                PreferenceHelper prefShared = new PreferenceHelper(this);
                String jwtToken = prefShared.getStr("jwtToken");
                /* GET JWT TOKEN */

                String receiverid = result.getContents();
                /*Create handle for the RetrofitInstance interface*/
                api service = RetrofitClient.getRetrofitInstance().create(api.class);
                Call<PaymentParkingModel> call = service.payments(jwtToken, receiverid);
                call.enqueue(new Callback<PaymentParkingModel>() {
                    @Override
                    public void onResponse(Call<PaymentParkingModel> call, Response<PaymentParkingModel> response) {
                        Toast.makeText(HomeUser.this, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<PaymentParkingModel> call, Throwable t) {
                        Toast.makeText(HomeUser.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                });
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
