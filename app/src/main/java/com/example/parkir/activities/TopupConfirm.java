package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupConfirm extends AppCompatActivity {

    TextView txtPaymentGateway;
    Button btnConfirm;
    EditText etNominal;
    api mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_confirm);

        txtPaymentGateway = findViewById(R.id.txtPaymentGateway);
        btnConfirm = findViewById(R.id.btnConfirm);
        etNominal = findViewById(R.id.textNominal);

        Intent intent = getIntent();
        final String payment_gateway = intent.getStringExtra("payment_gateway");

        txtPaymentGateway.setText(payment_gateway);
        btnConfirm.setText("Confirm with " + payment_gateway);

        /* GET JWT TOKEN */
        PreferenceHelper prefShared2 = new PreferenceHelper(this);
        final String jwtToken = prefShared2.getStr("jwtToken");
        /* GET JWT TOKEN */

        mApiInterface = RetrofitClient.getRetrofitInstance().create(api.class);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNominal.getText().toString().trim())) {
                    Toast.makeText(TopupConfirm.this, "Nominal wajib di isi!", Toast.LENGTH_LONG).show();
                } else if (etNominal.getText().toString().equals("0")) {
                    Toast.makeText(TopupConfirm.this, "Nominal tidak boleh 0!", Toast.LENGTH_LONG).show();
                } else {
                    Call<PaymentTopupModel> postCall = mApiInterface.payment_topup(jwtToken, payment_gateway, Integer.parseInt(etNominal.getText().toString()));
                    postCall.enqueue(new Callback<PaymentTopupModel>() {
                        @Override
                        public void onResponse(Call<PaymentTopupModel> call, Response<PaymentTopupModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(TopupConfirm.this, "Success Top-Up " + response.body().getData().getDatas().toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TopupConfirm.this, "Gagal Top-Up", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PaymentTopupModel> call, Throwable t) {
                            Toast.makeText(TopupConfirm.this, "Error, harap periksa koneksi Internet Anda!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String roleid = prefShared.getStr("roleid");
        if (roleid.equals("1")) {
            Intent intent = new Intent(TopupConfirm.this, HomeKangParkir.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (roleid.equals("2")) {
            Intent intent = new Intent(TopupConfirm.this, HomeUser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void duaPuluh(View view){
        etNominal.setText("20000");
    }

    public void limaPuluh(View view){
        etNominal.setText("50000");
    }

    public void seratus(View view){
        etNominal.setText("100000");
    }
}