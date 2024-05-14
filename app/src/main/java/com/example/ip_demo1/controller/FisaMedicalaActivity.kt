package com.example.ip_demo1.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableRow
import com.example.ip_demo1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FisaMedicalaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fisa_medicala)

        val button=findViewById<FloatingActionButton>(R.id.btBack_to_home)

        val to_recomButton = findViewById<TableRow>(R.id.row1)


        button.setOnClickListener {
            val intent = Intent(this, MenuActivityJ::class.java)
            startActivity(intent)
        }

        to_recomButton.setOnClickListener {
            val intent = Intent(this, RecommendationActivityJ::class.java)
            startActivity(intent)
        }

    }
}