package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkir.R;
import com.example.parkir.helpers.PreferenceHelper;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String roleid = prefShared.getStr("roleid") == null ? "0" : prefShared.getStr("roleid");
        if (roleid != "0") {
            if (roleid == "1") {
                Intent i = new Intent(MainActivity.this, HomeKangParkir.class);
                startActivity(i);
            } else {
                Intent i = new Intent(MainActivity.this, HomeUser.class);
                startActivity(i);
            }
        }
        Button login_page = findViewById(R.id.home_login);
        login_page.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_login:
                Intent moveLogin = new Intent(MainActivity.this, LoginPage.class);
                startActivity(moveLogin);
        }
    }

    public void daftarKangParkir(View view) {
        Intent i = new Intent(MainActivity.this, DaftarKangParkir.class);
        startActivity(i);
    }

    public void daftarUser(View view) {
        Intent i = new Intent(MainActivity.this, DaftarUser.class);
        startActivity(i);
    }
}
