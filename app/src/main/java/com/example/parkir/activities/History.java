package com.example.parkir.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.adapters.HistoryAdapter;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.history.Data_;
import com.example.parkir.model.history.HistoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {

    private HistoryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /* GET JWT TOKEN */
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String roleid = prefShared.getStr("roleid");
        /* GET JWT TOKEN */


        if (roleid.equals("1")) {
            /*Create handle for the RetrofitInstance interface*/
            api service = RetrofitClient.getRetrofitInstance().create(api.class);
            /* GET JWT TOKEN */
            PreferenceHelper prefShared2 = new PreferenceHelper(this);
            String jwtToken = prefShared2.getStr("jwtToken");
            /* GET JWT TOKEN */

            Call<HistoryModel> call = service.payments_income(jwtToken);

            call.enqueue(new Callback<HistoryModel>() {
                @Override
                public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
                    Toast.makeText(History.this, "History Transaksi Parkir", Toast.LENGTH_SHORT).show();
                    generateDataList(response.body().getData().getDatas());
                }

                @Override
                public void onFailure(Call<HistoryModel> call, Throwable t) {
                    Toast.makeText(History.this, "Error, harap periksa koneksi Internet Anda!" + t, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            /*Create handle for the RetrofitInstance interface*/
            api service = RetrofitClient.getRetrofitInstance().create(api.class);
            /* GET JWT TOKEN */
            PreferenceHelper prefShared3 = new PreferenceHelper(this);
            String jwtToken = prefShared3.getStr("jwtToken");
            /* GET JWT TOKEN */
            Call<HistoryModel> call = service.payments_expend(jwtToken);

            call.enqueue(new Callback<HistoryModel>() {
                @Override
                public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
                    Toast.makeText(History.this, "History Transaksi Parkir", Toast.LENGTH_SHORT).show();
                    generateDataList(response.body().getData().getDatas());
                }

                @Override
                public void onFailure(Call<HistoryModel> call, Throwable t) {
                    Toast.makeText(History.this, "Error, harap periksa koneksi Internet Anda!" + t, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Data_> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        adapter = new HistoryAdapter(History.this, dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
