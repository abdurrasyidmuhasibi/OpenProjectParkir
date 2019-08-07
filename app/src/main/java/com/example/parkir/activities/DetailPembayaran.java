package com.example.parkir.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.payment.PaymentModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembayaran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);

        /* GET JWT TOKEN */
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String jwtToken = prefShared.getStr("jwtToken");
        /* GET JWT TOKEN */

        /*Create handle for the RetrofitInstance interface*/
        api service = RetrofitClient.getRetrofitInstance().create(api.class);
        Call<PaymentModel> call = service.payments(jwtToken);
        call.enqueue(new Callback<PaymentModel>() {
            @Override
            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
            }

            @Override
            public void onFailure(Call<PaymentModel> call, Throwable t) {
                Toast.makeText(DetailPembayaran.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        /* END LOAD CONTENT HOME */
    }
}
