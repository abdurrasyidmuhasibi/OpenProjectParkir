package com.example.parkir.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.parkir.R;
import com.example.parkir.helpers.PreferenceHelper;

public class SettingAkun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_logout);
    }

    public void myHome(View view){
        Intent i = new Intent(SettingAkun.this,HomeUser.class);
        startActivity(i);
    }

    public void clickLogout(View view){
        PreferenceHelper prefShared = new PreferenceHelper(this);
        prefShared.setStr("jwtToken", null);
        prefShared.setStr("roleid", null);
        prefShared.setStr("accountid", null);
        Intent i = new Intent(SettingAkun.this, MainActivity.class);
        startActivity(i);
    }
}
