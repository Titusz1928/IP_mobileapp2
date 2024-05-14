package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.ip_demo1.R;
import com.example.ip_demo1.controller.ChangeInformationJ;
import com.example.ip_demo1.controller.ChangePasswordActivityJ;
import com.example.ip_demo1.controller.LoginActivity;

public class SettingsFragmentJ extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_j, container, false);

        Button buttonLogout = view.findViewById(R.id.SETlogoutButton);
        Button buttonChangePassword = view.findViewById(R.id.SETresetpasswordButton);
        ImageView buttonChangeName = view.findViewById(R.id.imageView_nume);

        buttonChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ChangeInformationJ.class);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ChangePasswordActivityJ
                Intent intent = new Intent(requireContext(), ChangePasswordActivityJ.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
