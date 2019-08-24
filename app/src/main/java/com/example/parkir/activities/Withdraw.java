package com.example.parkir.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.paymenttopup.PaymentTopupModel;
import com.example.parkir.model.paymentwithdraw.PaymentWithdrawModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Withdraw extends AppCompatActivity {

    Button btnConfirm;
    EditText etNominal;
    api mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        btnConfirm = findViewById(R.id.btnConfirm);
        etNominal = findViewById(R.id.etNominal);

        btnConfirm.setText("Cairkan");

        /* GET JWT TOKEN */
        PreferenceHelper prefShared2 = new PreferenceHelper(this);
        final String jwtToken = prefShared2.getStr("jwtToken");
        /* GET JWT TOKEN */

        mApiInterface = RetrofitClient.getRetrofitInstance().create(api.class);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etNominal.getText().toString().trim())){
                    Toast.makeText(Withdraw.this, "Nominal wajib di isi!", Toast.LENGTH_LONG).show();
                }else if(etNominal.getText().toString().equals("0")){
                    Toast.makeText(Withdraw.this, "Nominal tidak boleh 0!", Toast.LENGTH_LONG).show();
                }else{
                    Call<PaymentWithdrawModel> postCall = mApiInterface.payment_withdraw(jwtToken, Integer.parseInt(etNominal.getText().toString()));
                    postCall.enqueue(new Callback<PaymentWithdrawModel>() {
                        @Override
                        public void onResponse(Call<PaymentWithdrawModel> call, Response<PaymentWithdrawModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(Withdraw.this, "Success withdraw " + response.body().getData().getDatas().toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Withdraw.this, "Gagal withdraw", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PaymentWithdrawModel> call, Throwable t) {
                            Toast.makeText(Withdraw.this, "Error, harap periksa koneksi Internet Anda!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}