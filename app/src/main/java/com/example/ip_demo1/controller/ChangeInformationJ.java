package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;

public class ChangeInformationJ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);

        Button buttonSaveData = findViewById(R.id.CINbcvSaveButton);

        buttonSaveData.setOnClickListener(v -> {
            Intent intent = new Intent(ChangeInformationJ.this, MenuActivityJ.class);
            startActivity(intent);
        });
    }
}
