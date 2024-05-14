package com.example.ip_demo1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.ip_demo1.R;

public class ProfileFragmentJ extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_j, container, false);

        CardView to_actualizare = view.findViewById(R.id.llActualizareCardView);
        CardView to_vizualizare = view.findViewById(R.id.llVizualizareCardView);

        to_actualizare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ActualizareActivityJ.class);
                startActivity(intent);
            }
        });

        to_vizualizare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), VizualizareActivityJ.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
