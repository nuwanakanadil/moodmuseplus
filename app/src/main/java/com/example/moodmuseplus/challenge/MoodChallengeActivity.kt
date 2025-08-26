package com.example.moodmuseplus.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.LinearProgressIndicator

class MoodChallengeActivity : AppCompatActivity() {

    private lateinit var grid: GridLayout
    private lateinit var progress: LinearProgressIndicator
    private lateinit var progressLabel: TextView
    private var done = BooleanArray(7) { false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mood_challenge)

        findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        grid = findViewById(R.id.grid_days)
        progress = findViewById(R.id.progress)
        progress.max = 7
        progressLabel = findViewById(R.id.tv_progress)

        // Toggle group
        val toggle = findViewById<MaterialButtonToggleGroup>(R.id.toggle_group)
        val btnPositive = findViewById<MaterialButton>(R.id.btn_positive)
        val btnCalm = findViewById<MaterialButton>(R.id.btn_calm)

        // Default selection: Positive
        toggle.check(R.id.btn_positive)
        buildDays(theme = "positive")

        toggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (checkedId) {
                btnPositive.id -> buildDays(theme = "positive")
                btnCalm.id -> buildDays(theme = "calm")
            }
        }
    }

    private fun buildDays(theme: String) {
        // reset UI state
        done = BooleanArray(7) { false }
        updateProgress()

        grid.removeAllViews()
        val inflater = LayoutInflater.from(this)

        // Titles + hints from resources (arrays.xml)
        val titles = when (theme) {
            "calm" -> resources.getStringArray(R.array.challenge_calm_titles)
            else -> resources.getStringArray(R.array.challenge_positive_titles)
        }
        val hints = when (theme) {
            "calm" -> resources.getStringArray(R.array.challenge_calm_hints)
            else -> resources.getStringArray(R.array.challenge_positive_hints)
        }

        repeat(7) { idx ->
            val v = inflater.inflate(R.layout.item_challenge_day, grid, false) as MaterialCardView

            val tvDayNum = v.findViewById<TextView>(R.id.tv_day_num)
            val tvTitle  = v.findViewById<TextView>(R.id.tv_title)
            val tvHint   = v.findViewById<TextView>(R.id.tv_hint)
            val ivDone   = v.findViewById<ImageView>(R.id.iv_done)

            tvDayNum.text = getString(R.string.challenge_day_num, idx + 1)
            tvTitle.text = titles[idx]
            tvHint.text  = hints[idx]
            ivDone.visibility = View.GONE

            // Toggle UI on tap
            v.setOnClickListener {
                done[idx] = !done[idx]
                ivDone.visibility = if (done[idx]) View.VISIBLE else View.GONE
                v.strokeColor = if (done[idx])
                    getColor(R.color.accent_10)
                else
                    getColor(R.color.secondary_30_softgold)
                updateProgress()
            }

            // GridLayout params (2 columns)
            val lp = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8, 8, 8, 8)
            }
            v.layoutParams = lp
            grid.addView(v)
        }
    }

    private fun updateProgress() {
        val count = done.count { it }
        progress.setProgressCompat(count, true)
        progressLabel.text = getString(R.string.challenge_progress_fmt, count, 7)
    }
}
