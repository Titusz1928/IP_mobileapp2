package com.example.ip_demo1.controller;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ip_demo1.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FisaMedicalaWebActivityJ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisa_medicala_web);

        WebView webview = findViewById(R.id.llWebView);

        // Open the HTML file using resources
        InputStream inputStream = getResources().openRawResource(R.raw.fisa_pacient_pv);

        // Read the contents of the HTML file
        String htmlContent = convertStreamToString(inputStream);

        // Display the HTML content (e.g., in a WebView)
        webview.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
