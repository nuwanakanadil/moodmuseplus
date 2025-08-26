package com.example.moodmuseplus.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import com.example.moodmuseplus.journal.JournalActivity
import com.example.moodmuseplus.log.MoodLogActivity
import com.example.moodmuseplus.mood.MoodSelectActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.moodmuseplus.meditation.MeditationActivity
import com.example.moodmuseplus.art.ArtGalleryActivity
import com.example.moodmuseplus.quotes.QuoteLibraryActivity
import com.example.moodmuseplus.challenge.MoodChallengeActivity
import com.example.moodmuseplus.profile.ProfileActivity
import com.example.moodmuseplus.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom.selectedItemId = R.id.nav_home
        bottom.setOnItemSelectedListener { item ->
            bounce(bottom, item.itemId) // animate selected item

            when (item.itemId) {
                R.id.nav_home -> true // already here
                R.id.nav_log -> { startActivity(Intent(this, MoodLogActivity::class.java)); true }
                R.id.nav_journal -> { startActivity(Intent(this, JournalActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)); true }
                R.id.nav_profile -> { startActivity(Intent(this, ProfileActivity::class.java)); true }
                else -> false
            }
        }

        // Cards
        setCardClick(R.id.card_mood)       { startActivity(Intent(this, MoodSelectActivity::class.java)) }
        setCardClick(R.id.card_log)        { startActivity(Intent(this, MoodLogActivity::class.java)) }
        setCardClick(R.id.card_journal)    { startActivity(Intent(this, JournalActivity::class.java)) }
        setCardClick(R.id.card_meditation) { startActivity(Intent(this, MeditationActivity::class.java)) }
        setCardClick(R.id.card_art)        { startActivity(Intent(this, ArtGalleryActivity::class.java)) }
        setCardClick(R.id.card_quotes)     { startActivity(Intent(this, QuoteLibraryActivity::class.java)) }
        setCardClick(R.id.card_challenge)  { startActivity(Intent(this, MoodChallengeActivity::class.java)) }
        setCardClick(R.id.card_profile)    { startActivity(Intent(this, ProfileActivity::class.java)) }
        setCardClick(R.id.card_settings)   { startActivity(Intent(this, SettingsActivity::class.java)) }

        findViewById<FloatingActionButton>(R.id.fab_new_mood).setOnClickListener {
            startActivity(Intent(this, MoodSelectActivity::class.java))
        }
    }

    private fun setCardClick(id: Int, onClick: () -> Unit) {
        findViewById<View>(id).setOnClickListener { onClick() }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private fun bounce(bottom: BottomNavigationView, itemId: Int) {
        val navMenu = bottom.menu
        val navBar = bottom.getChildAt(0) as? ViewGroup ?: return

        var targetView: View? = null
        for (i in 0 until navMenu.size()) {
            if (navMenu.getItem(i).itemId == itemId) {
                targetView = navBar.getChildAt(i)
                break
            }
        }
        targetView?.animate()
            ?.scaleX(1.08f)?.scaleY(1.08f)
            ?.setDuration(120)
            ?.withEndAction {
                targetView.animate().scaleX(1f).scaleY(1f).setDuration(120).start()
            }?.start()
    }
}
