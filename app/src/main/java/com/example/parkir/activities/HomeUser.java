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
import com.example.parkir.model.account.AccountModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

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
                txtBalance.setText("Rp. " + response.body().getData().getDatas().getBalance());
                txtName.setText("" + response.body().getData().getDatas().getFullName());
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(HomeUser.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }


        });
        /* END LOAD CONTENT HOME */
    }

    public void myScan(View view) {
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    public void myAkun(View view) {
        Intent i = new Intent(HomeUser.this, SettingAkun.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            } else {
                /* GET JWT TOKEN */
                PreferenceHelper prefShared = new PreferenceHelper(this);
                String jwtToken = prefShared.getStr("jwtToken");
                /* GET JWT TOKEN */

                /* KANG PARKIR ID */
                Integer accountid = parseInt(result.getContents());
                /*Create handle for the RetrofitInstance interface*/
                api service = RetrofitClient.getRetrofitInstance().create(api.class);
                Call<AccountModel> call = service.account_parkir(jwtToken, accountid);
                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                        if (response.body().getData().getDatas().getAssignment().equals(null)) {
                            Toast.makeText(HomeUser.this, "Akun anda bukan akun tukang parkir.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeUser.this, "Sukses, tukang parkir anda adalah " + response.body().getData().getDatas().getFullName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeUser.this, DetailPembayaran.class);
                            intent.putExtra("accountid", response.body().getData().getDatas().getId().toString());
                            intent.putExtra("name", response.body().getData().getDatas().getFullName());
                            intent.putExtra("location_name", response.body().getData().getDatas().getAssignment().getLocationName());
                            intent.putExtra("location_address", response.body().getData().getDatas().getAssignment().getLocationAddress() + "," + response.body().getData().getDatas().getAssignment().getDistrict() + "," + response.body().getData().getDatas().getAssignment().getCity() + ",");
                            intent.putExtra("nominal", "Rp. 2000");
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Toast.makeText(HomeUser.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                });
                /* END LOAD CONTENT HOME */
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
