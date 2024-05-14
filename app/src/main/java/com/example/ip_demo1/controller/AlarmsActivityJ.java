package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AlarmsActivityJ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        FloatingActionButton button = findViewById(R.id.ALAbcvBackButton);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(AlarmsActivityJ.this, MenuActivityJ.class);
            startActivity(intent);
        });
    }
}
