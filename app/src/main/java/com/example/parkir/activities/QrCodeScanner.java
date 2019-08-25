package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.RetrofitClient;
import com.example.parkir.api.api;
import com.example.parkir.helpers.PreferenceHelper;
import com.example.parkir.model.account.AccountModel;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class QrCodeScanner extends AppCompatActivity {

    api mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);

        DecoratedBarcodeView qrView = findViewById(R.id.qr_scanner_view);
        CameraSettings s = new CameraSettings();
        s.setRequestedCameraId(0); // front/back/etc
        qrView.getBarcodeView().setCameraSettings(s);
        qrView.setStatusText("Please place a QR code inside rectangle to scan it.");
        qrView.resume();

        qrView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
//                Toast.makeText(QrCodeScanner.this, ""+ result.toString(), Toast.LENGTH_SHORT).show();

                /* GET JWT TOKEN */
                PreferenceHelper prefShared = new PreferenceHelper(QrCodeScanner.this);
                String jwtToken = prefShared.getStr("jwtToken");
                /* GET JWT TOKEN */

                /* SPLIT */
                final String s = result.toString();
                String[] arrayString = s.split(";");

                final String fcmToken = arrayString[0];
                Integer accountid = parseInt(arrayString[1]);

                /* KANG PARKIR ID */
//                Integer accountid = parseInt(result.getContents());
                Toast.makeText(QrCodeScanner.this, "" + accountid, Toast.LENGTH_SHORT).show();


                /*Create handle for the RetrofitInstance interface*/
                api service = RetrofitClient.getRetrofitInstance().create(api.class);
                Call<AccountModel> call = service.account_parkir(jwtToken, accountid);
                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                        if (response.body().getData().getDatas().getAssignment().equals(null)) {
                            Toast.makeText(QrCodeScanner.this, "Akun anda bukan akun tukang parkir.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(QrCodeScanner.this, "Sukses, tukang parkir anda adalah " + response.body().getData().getDatas().getFullName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QrCodeScanner.this, DetailPembayaran.class);
                            intent.putExtra("fcmToken", fcmToken);
                            intent.putExtra("accountid", response.body().getData().getDatas().getId().toString());
                            intent.putExtra("name", response.body().getData().getDatas().getFullName());
                            intent.putExtra("location_name", response.body().getData().getDatas().getAssignment().getLocationName());
                            intent.putExtra("location_address", response.body().getData().getDatas().getAssignment().getLocationAddress() + "," + response.body().getData().getDatas().getAssignment().getDistrict() + "," + response.body().getData().getDatas().getAssignment().getCity() + ",");
                            intent.putExtra("nominal", "Rp. 0");
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Toast.makeText(QrCodeScanner.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                });
                /* END LOAD CONTENT HOME */

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        });
    }
}