package com.example.parkir.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.parkir.R;

public class HomeKangParkir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kang_parkir);
    }

    public void myQrCode(View view){
        Intent i = new Intent(HomeKangParkir.this, QrCode.class);
        startActivity(i);
    }
}
