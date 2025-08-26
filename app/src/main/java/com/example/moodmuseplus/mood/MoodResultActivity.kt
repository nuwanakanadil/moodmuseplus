package com.example.moodmuseplus.mood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MoodResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mood_result)

        val emoji = intent.getStringExtra("emoji") ?: "🙂"
        val colorTag = intent.getStringExtra("colorTag") // may be null

        val tvMood = findViewById<TextView>(R.id.tv_mood_label)
        val tvQuote = findViewById<TextView>(R.id.tv_quote)
        val tvTip = findViewById<TextView>(R.id.tv_tip)
        val ivArt = findViewById<ImageView>(R.id.iv_art)

        // 1) Label
        val moodName = moodNameFromEmoji(emoji)
        tvMood.text = getString(R.string.mood_result_header, emoji, moodName)

        // 2) Quote + tip
        val (quote, tip) = contentFor(moodName)
        tvQuote.text = "“$quote”"
        tvTip.text = tip

        // 3) Optional: tint the hero art edge based on colorTag
        // (we use a tinted gradient overlay on the placeholder)
        when (colorTag) {
            "butter" -> ivArt.setColorFilter(resources.getColor(R.color.mood_butter, theme))
            "honey"  -> ivArt.setColorFilter(resources.getColor(R.color.mood_honey, theme))
            "amber"  -> ivArt.setColorFilter(resources.getColor(R.color.mood_amber, theme))
            "orange" -> ivArt.setColorFilter(resources.getColor(R.color.mood_orange, theme))
            "bronze" -> ivArt.setColorFilter(resources.getColor(R.color.mood_bronze, theme))
            "sand"   -> ivArt.setColorFilter(resources.getColor(R.color.mood_sand, theme))
            else     -> ivArt.clearColorFilter()
        }

        // 4) Playlist button → open YouTube search
        findViewById<Button>(R.id.btn_play_playlist).setOnClickListener {
            val query = when (moodName) {
                "Calm" -> "calm ambient playlist"
                "Happy" -> "happy upbeat playlist"
                "Energized" -> "workout energy playlist"
                "Sad" -> "comforting songs playlist"
                "Anxious" -> "anxiety relief breathing music"
                "Relaxed" -> "lofi chill playlist"
                "Loved" -> "romantic soft playlist"
                "Tired" -> "focus low energy playlist"
                "Angry" -> "release stress playlist"
                else -> "mood booster playlist"
            }
            val url = "https://www.youtube.com/results?search_query=" +
                    URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        // 5) Done → back to Home
        findViewById<Button>(R.id.btn_done).setOnClickListener { finish() }
    }

    private fun moodNameFromEmoji(e: String): String = when (e) {
        "😊" -> "Happy"
        "😌" -> "Calm"
        "⚡" -> "Energized"
        "😔" -> "Sad"
        "😟" -> "Anxious"
        "🫶" -> "Relaxed"
        "🥰" -> "Loved"
        "🥱" -> "Tired"
        "😠" -> "Angry"
        else -> "Neutral"
    }

    private fun contentFor(mood: String): Pair<String, String> = when (mood) {
        "Happy" -> "Joy is contagious—let yours shine." to "Snap a photo of something that made you smile and add it to your journal."
        "Calm" -> "In quiet moments, clarity grows." to "Try 4–6 breathing: inhale 4, exhale 6 for one minute."
        "Energized" -> "Energy flows where attention goes." to "Channel it: list the top 3 things you’ll do in the next hour."
        "Sad" -> "This feeling is valid, and it will pass." to "Write a short note to yourself like you would to a friend."
        "Anxious" -> "Name it to tame it." to "Label 5 things you see, 4 you feel, 3 you hear, 2 you smell, 1 you taste."
        "Relaxed" -> "Softness is strength too." to "Stretch your shoulders and jaw; let them drop with the next exhale."
        "Loved" -> "Being loved starts with being you." to "Send a ‘thank you’ text to someone who held space for you."
        "Tired" -> "Rest is productive." to "Close your eyes for 60 seconds and focus on warm breaths."
        "Angry" -> "Anger points to a boundary." to "Do 10 slow shoulder rolls before responding to anything."
        else -> "Every mood tells a story." to "Note one small win from today, no matter how tiny."
    }
}
