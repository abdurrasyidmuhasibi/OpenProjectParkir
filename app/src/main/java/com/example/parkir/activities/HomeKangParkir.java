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

public class HomeKangParkir extends AppCompatActivity {
    api mApiInterface;
    TextView txtBalance, txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kang_parkir);
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
                txtBalance.setText("Rp. " + response.body().getData().getDatas().getBalance());
                txtName.setText("" + response.body().getData().getDatas().getFullName());
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(HomeKangParkir.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }


        });
        /* END LOAD CONTENT HOME */
    }

    public void myQrCode(View view) {
        Intent i = new Intent(HomeKangParkir.this, QrCode.class);
        startActivity(i);
    }

    public void myAkun(View view) {
        Intent i = new Intent(HomeKangParkir.this, SettingAkun.class);
        startActivity(i);
    }

    public void topup(View view) {
        Intent i = new Intent(HomeKangParkir.this, Topup.class);
        startActivity(i);
        finish();
    }

    public void withdraw(View view) {
        Intent i = new Intent(HomeKangParkir.this, Withdraw.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
