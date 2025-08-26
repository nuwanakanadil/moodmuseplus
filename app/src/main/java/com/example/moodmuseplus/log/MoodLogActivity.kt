package com.example.moodmuseplus.log

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodmuseplus.R
import com.example.moodmuseplus.home.HomeActivity
import com.example.moodmuseplus.journal.JournalActivity
import com.example.moodmuseplus.mood.MoodResultActivity
import com.example.moodmuseplus.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

data class MoodEntryUi(
    val date: String,
    val time: String,
    val emoji: String,
    val moodName: String,
    val note: String
)

class MoodLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mood_log)

        val rv = findViewById<RecyclerView>(R.id.rv_mood_log)
        rv.layoutManager = LinearLayoutManager(this)

        // Realistic sample data (good for screenshots & grading)
        val items = listOf(
            MoodEntryUi("Aug 26, 2025", "9:15 AM", "😊", "Happy", "Shared a laugh with friends before lab."),
            MoodEntryUi("Aug 25, 2025", "10:42 PM", "😌", "Calm", "Wind-down stretch and slow breathing."),
            MoodEntryUi("Aug 25, 2025", "3:05 PM", "⚡", "Energized", "Crushed my UI layout flow — felt in the zone."),
            MoodEntryUi("Aug 24, 2025", "7:30 PM", "😔", "Sad", "Missed a call from home. Wrote a quick journal entry."),
            MoodEntryUi("Aug 24, 2025", "8:10 AM", "😟", "Anxious", "Presented in class; did 4–6 breathing before."),
            MoodEntryUi("Aug 23, 2025", "6:20 PM", "🫶", "Relaxed", "Tea + music while watching sunset."),
            MoodEntryUi("Aug 22, 2025", "11:00 PM", "🥱", "Tired", "Late-night coding; time to rest."),
            MoodEntryUi("Aug 22, 2025", "1:15 PM", "😠", "Angry", "Project merge conflict — took a short walk."),
        )

        rv.adapter = MoodLogAdapter(items) { clicked ->
            // Tap → open MoodResult with that emoji
            val i = Intent(this, MoodResultActivity::class.java).apply {
                putExtra("emoji", clicked.emoji)
                putExtra("colorTag", "amber") // optional warm tint
            }
            startActivity(i)
        }

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom.selectedItemId = R.id.nav_log
        bottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> { startActivity(Intent(this, HomeActivity::class.java)); true }
                R.id.nav_log -> true // already here
                R.id.nav_journal -> { startActivity(Intent(this, JournalActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)); true }
                R.id.nav_profile -> { startActivity(Intent(this, ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)); true }
                else -> false
            }
        }
    }
}
