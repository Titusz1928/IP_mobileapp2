package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecommendationActivityJ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        FloatingActionButton button = findViewById(R.id.RECbcvBackButton);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(RecommendationActivityJ.this, MenuActivityJ.class);
            startActivity(intent);
        });
    }
}
