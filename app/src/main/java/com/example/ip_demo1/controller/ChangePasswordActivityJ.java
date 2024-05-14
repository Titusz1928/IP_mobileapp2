package com.example.ip_demo1.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

import com.example.ip_demo1.R;

public class ChangePasswordActivityJ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_j);

        Button buttonBack = findViewById(R.id.CPAifcvBackPassword);
        Button buttonConfirm = findViewById(R.id.CPAifcvConfirmPassword);

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePasswordActivityJ.this, MenuActivityJ.class);
            startActivity(intent);
        });

        buttonConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePasswordActivityJ.this, MenuActivityJ.class);
            startActivity(intent);
        });
    }
}