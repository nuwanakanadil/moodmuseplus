package com.example.moodmuseplus.art

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodmuseplus.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

data class ArtItem(
    val title: String,
    val mood: String,        // e.g., "Happy", "Calm"
    val emoji: String,
    val imageName: String
)

class ArtGalleryActivity : AppCompatActivity() {

    private lateinit var adapter: ArtAdapter
    private lateinit var allItems: List<ArtItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_art_gallery)

        // Toolbar back
        findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        // Sample curated items (good for screenshots + rubric)
        allItems = listOf(
            ArtItem("Sunrise Flow",    "Happy",    "😊", "art_happy_sunrise"),
            ArtItem("Lemonade Light",  "Happy",    "😊", "art_happy_lemonade"),
            ArtItem("Quiet Lake",      "Calm",     "😌", "art_calm_lake"),
            ArtItem("Warm Breeze",     "Calm",     "😌", "art_calm_breeze"),
            ArtItem("Scarlet Spark",   "Energetic","⚡", "art_energetic_scarlet"),
            ArtItem("Gold Rhythm",     "Energetic","⚡", "art_energetic_gold"),
            ArtItem("Indigo Rain",     "Sad",      "😔", "art_sad_indigo"),
            ArtItem("Midnight Violet", "Sad",      "😔", "art_sad_violet"),
            ArtItem("Balanced Stone",  "Neutral",  "🙂", "art_neutral_stone"),
            ArtItem("Soft Cotton",     "Neutral",  "🙂", "art_neutral_cotton"),
            ArtItem("Ocean Hush",      "Calm",     "😌", "art_calm_ocean"),
            ArtItem("Saffron Joy",     "Happy",    "😊", "art_happy_saffron")
        )

        val rv = findViewById<RecyclerView>(R.id.rv_art)
        rv.layoutManager = GridLayoutManager(this, 2)
        adapter = ArtAdapter(allItems) { clicked ->
            Toast.makeText(
                this,
                getString(R.string.art_toast_view, clicked.title, clicked.emoji, clicked.mood),
                Toast.LENGTH_SHORT
            ).show()
            // Later: open a full-screen preview page if you want
        }
        rv.adapter = adapter

        setupFilters()
    }

    private fun setupFilters() {
        val cg = findViewById<ChipGroup>(R.id.chips_filter)

        fun addFilter(text: String) {
            val chip = Chip(this, null, com.google.android.material.R.style.Widget_Material3_Chip_Filter).apply {
                setText(text)
                isCheckable = true
                isCheckedIconVisible = false
                setChipBackgroundColorResource(R.color.chip_bg_selector)
                setTextColor(resources.getColorStateList(R.color.chip_text_selector, theme))
                setChipStrokeColorResource(R.color.secondary_30_softgold)
                chipStrokeWidth = resources.getDimension(R.dimen.stroke_1dp)
                // IMPORTANT for ChipGroup measuring
                layoutParams = ChipGroup.LayoutParams(
                    ChipGroup.LayoutParams.WRAP_CONTENT,
                    ChipGroup.LayoutParams.WRAP_CONTENT
                )
            }
            cg.addView(chip)
        }

        // Add filters (+ "All")
        val filters = listOf(getString(R.string.filter_all), "Happy", "Calm", "Energetic", "Sad", "Neutral")
        filters.forEach { addFilter(it) }

        cg.setOnCheckedStateChangeListener { group, _ ->
            val checkedId = group.checkedChipId
            val label = group.findViewById<Chip>(checkedId)?.text?.toString() ?: getString(R.string.filter_all)
            applyFilter(label)
        }
    }

    private fun applyFilter(label: String) {
        if (label == getString(R.string.filter_all)) {
            adapter.submit(allItems)
            return
        }
        adapter.submit(allItems.filter { it.mood.equals(label, ignoreCase = true) })
    }
}
