package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.account.AccountModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupDashboard extends AppCompatActivity {

    TextView saldo;

    private String KEY_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_topup_dashboard);

        saldo = (TextView) findViewById(R.id.tvSaldo);

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
                saldo.setText("Rp. " + response.body().getData().getDatas().getBalance());
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(TopupDashboard.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }


        });
        /* END LOAD CONTENT HOME */
    }

    public void clickAtm(View view) {
        Intent i = new Intent(TopupDashboard.this, Topup.class);
        i.putExtra(KEY_ID, "1");
        startActivity(i);
    }

    public void clickMinimarket(View view) {
        Intent i = new Intent(TopupDashboard.this, Topup.class);
        i.putExtra(KEY_ID, "2");
        startActivity(i);
    }

    public void clickDebit(View view) {
        Intent i = new Intent(TopupDashboard.this, Topup.class);
        i.putExtra(KEY_ID, "3");
        startActivity(i);
    }

    public void clickOthers(View view) {
        Intent i = new Intent(TopupDashboard.this, Topup.class);
        i.putExtra(KEY_ID, "4");
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String roleid = prefShared.getStr("roleid");
        if (roleid.equals("1")) {
            Intent intent = new Intent(TopupDashboard.this, HomeKangParkir.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (roleid.equals("2")) {
            Intent intent = new Intent(TopupDashboard.this, HomeUser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
