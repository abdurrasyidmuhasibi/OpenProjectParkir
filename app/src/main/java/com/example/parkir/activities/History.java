package com.example.parkir.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitAdapter;
import com.example.parkir.RetrofitClient;
import com.example.parkir.RetrofitInstance;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.account.AccountModel;
import com.example.parkir.model.history.HistoryList;
import com.example.parkir.model.history.HistoryModel;
import com.example.parkir.model.payment.PaymentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class History extends AppCompatActivity {

    private RetrofitAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api service = RetrofitInstance.getRetrofitInstance().create(api.class);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String jwtToken = prefShared.getStr("jwtToken");

        Call<HistoryList> call = service.payments_income(jwtToken);

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<HistoryList>() {
            @Override
            public void onResponse(Call<HistoryList> call, Response<HistoryList> response) {
                generateHistoryList(response.body().getHistoryArrayList());
            }

            @Override
            public void onFailure(Call<HistoryList> call, Throwable t) {
                Toast.makeText(History.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateHistoryList(ArrayList<HistoryModel> historyDataList) {
        recyclerView = (RecyclerView) findViewById(R.id.rv_history_list);

        adapter = new RetrofitAdapter(historyDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(History.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }
}
