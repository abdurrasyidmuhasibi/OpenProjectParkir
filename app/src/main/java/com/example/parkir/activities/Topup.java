package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.adapters.GatewayAdapter;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.paymenttype.Data_;
import com.example.parkir.model.paymenttype.PaymentGatewayModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topup extends AppCompatActivity {

    private GatewayAdapter adapter;
    private RecyclerView recyclerView;
    Call<PaymentGatewayModel> call;
    TopupDashboard td = new TopupDashboard();
    private String KEY_ID = "ID";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);

        /*Create handle for the RetrofitInstance interface*/
        api service = RetrofitClient.getRetrofitInstance().create(api.class);
        /* GET JWT TOKEN */
        PreferenceHelper prefShared3 = new PreferenceHelper(this);
        final String jwtToken = prefShared3.getStr("jwtToken");
        /* GET JWT TOKEN */

        Bundle extras = getIntent().getExtras();
        id = extras.getString(KEY_ID);

        if (id.equals("1")) {
            call = service.payments_gateway_1();
        } else if (id.equals("2")) {
            call = service.payments_gateway_2();
        } else if (id.equals("3")) {
            call = service.payments_gateway_3();
        } else if (id.equals("4")) {
            call = service.payments_gateway_4();
        }

        call.enqueue(new Callback<PaymentGatewayModel>() {
            @Override
            public void onResponse(Call<PaymentGatewayModel> call, Response<PaymentGatewayModel> response) {
                Toast.makeText(Topup.this, "Success...", Toast.LENGTH_SHORT).show();
                generateDataList(response.body().getData().getDatas());
            }

            @Override
            public void onFailure(Call<PaymentGatewayModel> call, Throwable t) {
                Toast.makeText(Topup.this, "error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Data_> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        adapter = new GatewayAdapter(Topup.this, dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Topup.this, TopupDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}