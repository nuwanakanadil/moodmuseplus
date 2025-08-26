package com.example.moodmuseplus.settings

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        // Toolbar back
        findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        // Dark mode (demo only: just show toast)
        val switchDark = findViewById<MaterialSwitch>(R.id.switch_dark)
        switchDark.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                this,
                if (isChecked) getString(R.string.toast_dark_on) else getString(R.string.toast_dark_off),
                Toast.LENGTH_SHORT
            ).show()
        }

        // Reminder time picker (demo)
        val tvReminder = findViewById<TextView>(R.id.tv_reminder_time)
        tvReminder.setOnClickListener {
            val dialog = TimePickerDialog(
                this,
                { _, h, m ->
                    val hh = if (h % 12 == 0) 12 else (h % 12)
                    val ampm = if (h >= 12) "PM" else "AM"
                    val mm = m.toString().padStart(2, '0')
                    tvReminder.text = "$hh:$mm $ampm"
                    Toast.makeText(this, getString(R.string.toast_reminder_set), Toast.LENGTH_SHORT).show()
                },
                8, 0, false
            )
            dialog.show()
        }

        // Privacy buttons (demo)
        findViewById<TextView>(R.id.btn_export).setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_export_started), Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.btn_delete_all).setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_delete_all_confirm), Toast.LENGTH_SHORT).show()
        }

        // About version (dummy value ok for UI assignment)
        findViewById<TextView>(R.id.tv_version).text = "1.0.0"
    }
}
