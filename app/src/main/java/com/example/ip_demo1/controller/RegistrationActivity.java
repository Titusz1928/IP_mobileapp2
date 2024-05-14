package com.example.ip_demo1.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ip_demo1.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "MyTag";

    private long mLastClickTime = 0;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_no_passw);

        Log.d(TAG, "registration activity entered");

        //declaring buttons
        Button registrationButton = findViewById(R.id.REGifcvRegistrationButton);
        Button backButton = findViewById(R.id.REGifcvBackButton);
        //declaring inputs
        TextInputEditText emailEditText = findViewById(R.id.tilEmail);
        EditText numeEditText = findViewById(R.id.tlNumeEditText);
        EditText prenumeEditText = findViewById(R.id.tlPrenumeEditText);
        EditText ageEditText = findViewById(R.id.tlAgeEditText);
        EditText cnpEditText = findViewById(R.id.tlCNPEditText);
        EditText phoneEditText = findViewById(R.id.tlPhoneEditText);
        EditText countryEditText = findViewById(R.id.tlCountryEditText);
        EditText judetEditText = findViewById(R.id.tlJudetEditText);
        EditText cityEditText = findViewById(R.id.tlCityEditText);
        EditText streetEditText = findViewById(R.id.tlStreeetEditText);
        EditText profEditText = findViewById(R.id.tlProfesieEditText);
        EditText locmunEditText = findViewById(R.id.tlLocMunEditText);

        //executed when pressing the registration button
        registrationButton.setOnClickListener(v -> {
            //if not enough time passed we stop, its to prevent double clicks
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            Toast.makeText(RegistrationActivity.this, "Loading", Toast.LENGTH_LONG).show();

            registrationButton.setEnabled(false);
            registrationButton.setClickable(false);

            // Collect user input
            String email = emailEditText.getText().toString();
            String nume = numeEditText.getText().toString();
            String prenume = prenumeEditText.getText().toString();
            String age = ageEditText.getText().toString();
            String cnp = cnpEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String country = countryEditText.getText().toString();
            String judet = judetEditText.getText().toString();
            String city = cityEditText.getText().toString();
            String street = streetEditText.getText().toString();
            String prof = profEditText.getText().toString();
            String locmun = locmunEditText.getText().toString();
            // Get other user data similarly

            // Create a JSON object with user data
            JSONObject userData = new JSONObject();
            try {
                userData.put("adresa_email",email);
                userData.put("nume", nume);
                userData.put("prenume", prenume);
                userData.put("varsta",age);
                userData.put("cnp", cnp);
                userData.put("numar_telefon", phone);
                userData.put("tara",country);
                userData.put("judet", judet);
                userData.put("oras", city);
                userData.put("strada",street);
                userData.put("profesie", prof);
                userData.put("loc_munca", locmun);
                userData.put("tip_acces","pacient");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, userData.toString());

            url=getString(R.string.URLregistration);

            //sending the request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, userData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle successful response
                            Log.d(TAG, "Response: " + response.toString());

                            String message = response.optString("message", "Unknown message");


                            // Display the message in a Toast
                            Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error response
                    Log.e(TAG, "Volley error: " + error.getMessage());
                    Toast.makeText(RegistrationActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                }
            });

            Volley.newRequestQueue(this).add(request);
            registrationButton.setEnabled(true);
            registrationButton.setClickable(true);
        });

        //back to login screen
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        });



    }




}

