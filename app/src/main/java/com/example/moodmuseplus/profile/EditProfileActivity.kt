package com.example.moodmuseplus.profile

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val ivAvatar = findViewById<ImageView>(R.id.iv_avatar)
        val etName = findViewById<TextInputEditText>(R.id.et_name)
        val etTagline = findViewById<TextInputEditText>(R.id.et_tagline)
        val btnChangePhoto = findViewById<MaterialButton>(R.id.btn_change_photo)
        val btnSave = findViewById<MaterialButton>(R.id.btn_save)

        // Prefill from your Profile page (for now, demo values)
        etName.setText(getString(R.string.profile_you))
        etTagline.setText(getString(R.string.profile_tagline))

        btnChangePhoto.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_change_photo_demo), Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            // In this lab, just show a toast and finish
            Toast.makeText(this, getString(R.string.toast_profile_saved), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
