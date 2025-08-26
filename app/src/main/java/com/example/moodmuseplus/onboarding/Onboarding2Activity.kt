package com.example.moodmuseplus.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R

class Onboarding2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding2)

        findViewById<Button>(R.id.btn_back).setOnClickListener {
            finish() // go back to page 1
        }

        findViewById<Button>(R.id.btn_next).setOnClickListener {
            // We'll build Onboarding3Activity next page
            startActivity(Intent(this, Onboarding3Activity::class.java))
        }
    }
}
