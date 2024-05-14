package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;

public class ActualizareActivityJ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizare_j);

        Button saveButton = findViewById(R.id.ACTbcvSaveButton);

        saveButton.setOnClickListener(v -> {
            Intent intent = new Intent(ActualizareActivityJ.this, MenuActivityJ.class);
            startActivity(intent);
        });
    }
}
