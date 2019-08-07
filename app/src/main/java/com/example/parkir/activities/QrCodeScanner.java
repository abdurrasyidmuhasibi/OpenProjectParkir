package com.example.parkir.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QrCodeScanner extends AppCompatActivity {

    private Button btnScan;
    private TextView textViewNama, textViewTinggi;
    private IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);

        btnScan = (Button) findViewById(R.id.btn_scan);
        textViewNama = (TextView) findViewById(R.id.textViewNama);
        textViewTinggi = (TextView) findViewById(R.id.textViewTinggi);
    }

    public void myScan(View view){
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                try{
                    // converting the data json
                    JSONObject object = new JSONObject(result.getContents());
                    // atur nilai ke textviews
                    textViewNama.setText(object.getString("nama"));
                    textViewTinggi.setText(object.getString("tinggi"));
                }catch (JSONException e){
                    e.printStackTrace();
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
