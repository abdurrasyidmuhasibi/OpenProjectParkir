package com.example.parkir.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.parkir.R;

public class DaftarKangParkir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kang_parkir);
    }

    public void daftarPenugasan(View view){
        Intent i = new Intent(DaftarKangParkir.this,DaftarPenugasan.class);
        startActivity(i);
    }
}
