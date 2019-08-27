package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.parkir.R;

public class TopupDashboard extends AppCompatActivity {

    TextView saldo;

    private String KEY_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_topup_dashboard);

        saldo = (TextView) findViewById(R.id.tvSaldo);
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
}
