package com.example.covid19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class diri : AppCompatActivity() {

    private lateinit var back:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diri)

        back = findViewById(R.id.back3)
        back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
