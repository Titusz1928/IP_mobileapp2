package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;

public class ResetPasswordActivity2J extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);

        Button buttonBack = findViewById(R.id.RPA2ifcvBackButton);
        Button buttonNext = findViewById(R.id.RPA2ifcvConfirmButton);

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity2J.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity2J.this, ResetPasswordActivity3J.class);
            startActivity(intent);
        });

    }
}
