package com.example.covid19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.covid19.pasien.PasienActivity

class MainActivity : AppCompatActivity() {

    private lateinit var what:Button
    private lateinit var why:Button
    private lateinit var how:Button
    private lateinit var how2:Button
    private lateinit var pasien:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        what = findViewById(R.id.what)
        what.setOnClickListener {
            startActivity(Intent(this, covid::class.java))
        }
        why = findViewById(R.id.why)
        why.setOnClickListener {
            startActivity(Intent(this, gejala::class.java))
        }
        how = findViewById(R.id.how)
        how.setOnClickListener {
            startActivity(Intent(this, diri::class.java))
        }
        how2 = findViewById(R.id.how2)
        how2.setOnClickListener {
            startActivity(Intent(this, oranglain::class.java))
        }
        pasien = findViewById(R.id.pasien)
        pasien.setOnClickListener {
            startActivity(Intent(this, PasienActivity::class.java))
        }
    }
}
