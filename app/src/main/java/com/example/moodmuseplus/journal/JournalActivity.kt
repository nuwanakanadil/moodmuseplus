package com.example.moodmuseplus.journal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import com.example.moodmuseplus.home.HomeActivity
import com.example.moodmuseplus.log.MoodLogActivity
import com.example.moodmuseplus.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class JournalActivity : AppCompatActivity() {

    private var hasPhoto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_journal)

        val iv = findViewById<ImageView>(R.id.iv_photo)
        val btnAdd = findViewById<Button>(R.id.btn_add_photo)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val et = findViewById<EditText>(R.id.et_entry)

        btnAdd.setOnClickListener {
            // Prototype: toggle a visible placeholder photo (no permissions required)
            hasPhoto = !hasPhoto
            if (hasPhoto) {
                iv.visibility = View.VISIBLE
                iv.setImageResource(R.drawable.bg_mood_art_placeholder) // warm gradient as mock photo
                Toast.makeText(this, getString(R.string.journal_toast_photo_added), Toast.LENGTH_SHORT).show()
            } else {
                iv.visibility = View.GONE
                Toast.makeText(this, getString(R.string.journal_toast_photo_removed), Toast.LENGTH_SHORT).show()
            }
        }

        btnSave.setOnClickListener {
            val text = et.text?.toString()?.trim().orEmpty()
            if (text.isBlank()) {
                Toast.makeText(this, getString(R.string.journal_toast_empty), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.journal_toast_saved), Toast.LENGTH_SHORT).show()
                // stay on page for now (teacher can see result). You could finish() if you prefer.
            }
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val bottom = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom.selectedItemId = R.id.nav_journal
        bottom.setOnItemReselectedListener { /* no-op */ }
        bottom.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.nav_journal) return@setOnItemSelectedListener true
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)); true
                }
                R.id.nav_log -> {
                    startActivity(Intent(this, MoodLogActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)); true
                }
                R.id.nav_profile -> { startActivity(Intent(this, ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)); true }
                else -> false
            }
        }
    }
}
