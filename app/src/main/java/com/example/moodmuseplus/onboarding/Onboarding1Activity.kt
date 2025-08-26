package com.example.moodmuseplus.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R

class Onboarding1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding1)

        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener {
            startActivity(Intent(this, Onboarding2Activity::class.java))
        }
    }
}
