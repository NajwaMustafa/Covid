package com.example.covid19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class gejala : AppCompatActivity() {

    private lateinit var back2:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gejala)

        back2 = findViewById(R.id.back2)
        back2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
