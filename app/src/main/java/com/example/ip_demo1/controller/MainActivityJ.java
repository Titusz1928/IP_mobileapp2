package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;

public class MainActivityJ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.MAINifcvLoginButton);
        TextView buttonForgot = findViewById(R.id.MAINifcvForgotPasswordText);

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityJ.this, MenuActivityJ.class);
            startActivity(intent);
        });

        buttonForgot.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityJ.this, ResetPasswordActivity1J.class);
            startActivity(intent);
        });
    }
}
