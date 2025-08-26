package com.example.moodmuseplus.mood

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R

class MoodSelectActivity : AppCompatActivity() {

    private val emojiIds = intArrayOf(
        R.id.emoji_happy, R.id.emoji_calm, R.id.emoji_energized, R.id.emoji_sad, R.id.emoji_anxious,
        R.id.emoji_relaxed, R.id.emoji_loved, R.id.emoji_tired, R.id.emoji_angry, R.id.emoji_neutral
    )
    private val colorIds = intArrayOf(
        R.id.chip_butter, R.id.chip_honey, R.id.chip_amber, R.id.chip_orange, R.id.chip_bronze, R.id.chip_sand
    )

    private var selectedEmoji: TextView? = null
    private var selectedColorTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mood_select)

        // Emoji click wiring
        emojiIds.forEach { id ->
            findViewById<TextView>(id).setOnClickListener { v ->
                selectEmoji(v as TextView)
            }
        }

        // Color chips click wiring (we just remember the tag + show a subtle selection)
        colorIds.forEach { id ->
            findViewById<View>(id).setOnClickListener { v ->
                // clear previous selection state
                colorIds.forEach { cid -> findViewById<View>(cid).isSelected = false }
                v.isSelected = true
                selectedColorTag = v.tag?.toString()
            }
        }

        findViewById<Button>(R.id.btn_continue).setOnClickListener {
            val emoji = selectedEmoji?.text?.toString()
            if (emoji.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.mood_pick_prompt), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val color = selectedColorTag
            val intent = Intent(this, MoodResultActivity::class.java).apply {
                putExtra("emoji", emoji)
                putExtra("colorTag", color)
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }

    private fun selectEmoji(tv: TextView) {
        // clear old selection
        emojiIds.forEach { id -> findViewById<TextView>(id).isSelected = false }
        // mark new
        tv.isSelected = true
        selectedEmoji = tv
    }
}
