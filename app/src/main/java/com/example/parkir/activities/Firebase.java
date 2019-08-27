package com.example.parkir.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkir.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class Firebase extends AppCompatActivity {

    TextView txtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        txtToken = (TextView) findViewById(R.id.token);
        final String token = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(Firebase.this, "Token =" + token, Toast.LENGTH_SHORT).show();
        txtToken.setTextIsSelectable(true);
        txtToken.setText(token);
    }
}
