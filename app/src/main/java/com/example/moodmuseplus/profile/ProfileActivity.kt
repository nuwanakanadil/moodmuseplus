package com.example.moodmuseplus.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import com.example.moodmuseplus.home.HomeActivity
import com.example.moodmuseplus.log.MoodLogActivity
import com.example.moodmuseplus.journal.JournalActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

data class BadgeItem(
    val code: String,
    val title: String,
    val requirement: String,
    var unlocked: Boolean
)

class ProfileActivity : AppCompatActivity() {

    private lateinit var grid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit_profile -> {
                    startActivity(Intent(this, EditProfileActivity::class.java))
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out)
                    true
                }
                else -> false
            }
        }


        // Bottom nav wiring
        val bottom = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom.selectedItemId = R.id.nav_profile
        bottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> { startActivity(Intent(this, HomeActivity::class.java)); true }
                R.id.nav_log ->  { startActivity(Intent(this, MoodLogActivity::class.java)); true }
                R.id.nav_journal -> { startActivity(Intent(this, JournalActivity::class.java)); true }
                R.id.nav_profile -> true
                else -> false
            }
        }

        // ----- Dummy-but-meaningful profile data -----
        findViewById<TextView>(R.id.tv_name).text = getString(R.string.profile_you)
        findViewById<TextView>(R.id.tv_tagline).text = getString(R.string.profile_tagline)

        // Streak + stats
        findViewById<TextView>(R.id.tv_streak_days).text = "5"
        findViewById<TextView>(R.id.tv_stat_moods).text = "21"
        findViewById<TextView>(R.id.tv_stat_journal).text = "9"
        findViewById<TextView>(R.id.tv_stat_meditation).text = "6"

        // Achievements grid
        grid = findViewById(R.id.grid_badges)
        buildBadges(
            listOf(
                BadgeItem("mood_tracker_7", getString(R.string.badge_mood_tracker), getString(R.string.req_mood_tracker), unlocked = true),
                BadgeItem("zen_master_5", getString(R.string.badge_zen_master), getString(R.string.req_zen_master), unlocked = false),
                BadgeItem("creative_soul_3", getString(R.string.badge_creative_soul), getString(R.string.req_creative_soul), unlocked = true),
                BadgeItem("uplifter_5", getString(R.string.badge_uplifter), getString(R.string.req_uplifter), unlocked = false),
                BadgeItem("art_enth_10", getString(R.string.badge_art_enthusiast), getString(R.string.req_art_enth), unlocked = true),
                BadgeItem("quote_hunter_20", getString(R.string.badge_quote_hunter), getString(R.string.req_quote_hunter), unlocked = false)
            )
        )
    }

    private fun buildBadges(items: List<BadgeItem>) {
        grid.removeAllViews()
        val inflater = LayoutInflater.from(this)

        items.forEach { b ->
            val card = inflater.inflate(R.layout.item_badge, grid, false) as MaterialCardView
            val iv = card.findViewById<ImageView>(R.id.iv_badge)
            val tvTitle = card.findViewById<TextView>(R.id.tv_title)
            val tvReq = card.findViewById<TextView>(R.id.tv_req)
            val tvStatus = card.findViewById<TextView>(R.id.tv_status)

            tvTitle.text = b.title
            tvReq.text = b.requirement
            if (b.unlocked) {
                iv.setImageResource(R.drawable.ic_check_circle)
                tvStatus.text = getString(R.string.badge_unlocked)
                card.strokeColor = getColor(R.color.accent_10)
            } else {
                iv.setImageResource(R.drawable.ic_lock)
                tvStatus.text = getString(R.string.badge_locked)
                card.strokeColor = getColor(R.color.secondary_30_softgold)
            }

            card.setOnClickListener {
                Toast.makeText(this, "${b.title}: ${tvStatus.text}", Toast.LENGTH_SHORT).show()
            }

            val lp = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8, 8, 8, 8)
            }
            card.layoutParams = lp
            grid.addView(card)
        }
    }
}
