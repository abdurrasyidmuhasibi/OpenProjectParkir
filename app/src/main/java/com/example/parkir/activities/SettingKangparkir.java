package com.example.parkir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkir.R;

public class SettingKangparkir extends Fragment {
    View view;
    Button editProfil, editPenugasan, btnHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_setting_kangparkir, container, false);
        editProfil = (Button) view.findViewById(R.id.btn_edt_profilKangparkir);
        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditProfil.class);
                startActivity(i);
            }
        });
        editPenugasan = (Button) view.findViewById(R.id.btn_edt_penugasan);
        editPenugasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditPenugasan.class);
                startActivity(i);
            }
        });
        btnHistory = (Button) view.findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), History.class);
                startActivity(i);
            }
        });
        return view;
    }
}
