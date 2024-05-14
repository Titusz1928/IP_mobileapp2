package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;

public class ResetPasswordActivity1J extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Button buttonBack = findViewById(R.id.RPAifcvBackButton);
        Button buttonNext = findViewById(R.id.RPAifcvConfirmButton);

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity1J.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity1J.this, ResetPasswordActivity2J.class);
            startActivity(intent);
        });

    }
}
