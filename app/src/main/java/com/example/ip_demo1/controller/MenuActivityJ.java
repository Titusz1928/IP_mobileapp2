package com.example.ip_demo1.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ip_demo1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivityJ extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = findViewById(R.id.clBottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragmentJ());
                return true;
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new ProfileFragmentJ());
                return true;
            } else if (item.getItemId() == R.id.chat) {
                replaceFragment(new ChatFragmentJ());
                return true;
            } else if (item.getItemId() == R.id.settings) {
                replaceFragment(new SettingsFragmentJ());
                return true;
            }
            return false;
        });


        replaceFragment(new HomeFragmentJ());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.clFrameLayout, fragment)
                .commit();
    }
}
