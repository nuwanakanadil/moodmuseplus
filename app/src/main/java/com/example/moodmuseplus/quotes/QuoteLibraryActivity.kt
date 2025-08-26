package com.example.moodmuseplus.quotes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodmuseplus.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import androidx.core.widget.doOnTextChanged

data class QuoteItem(
    val text: String,
    val author: String,
    val mood: String,   // "Happy", "Calm", "Energetic", "Sad", "Neutral"
    val emoji: String   // "😊", "😌", etc.
)

class QuoteLibraryActivity : AppCompatActivity() {

    private lateinit var adapter: QuoteAdapter
    private lateinit var allQuotes: List<QuoteItem>

    // ❌ was: getString(...) here → NPE
    // ✅ now set after setContentView()
    private var currentMood: String = ""
    private var currentFilter: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quote_library)

        // Safe to access resources NOW
        currentMood = getString(R.string.filter_all)

        // Back arrow
        findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        // Realistic, mood-tagged quotes
        allQuotes = listOf(
            QuoteItem("Joy is not in things; it is in us.", "Richard Wagner", "Happy", "😊"),
            QuoteItem("The more you praise and celebrate your life, the more there is in life to celebrate.", "Oprah Winfrey", "Happy", "😊"),
            QuoteItem("Silence is a source of great strength.", "Lao Tzu", "Calm", "😌"),
            QuoteItem("Within you there is a stillness and a sanctuary you can retreat to at any time.", "Hermann Hesse", "Calm", "😌"),
            QuoteItem("Energy and persistence conquer all things.", "Benjamin Franklin", "Energetic", "⚡"),
            QuoteItem("The future depends on what you do today.", "Mahatma Gandhi", "Energetic", "⚡"),
            QuoteItem("Tears are words that need to be written.", "Paulo Coelho", "Sad", "😔"),
            QuoteItem("Even the darkest night will end and the sun will rise.", "Victor Hugo", "Sad", "😔"),
            QuoteItem("Happiness is not a matter of intensity but of balance and order and rhythm and harmony.", "Thomas Merton", "Neutral", "🙂"),
            QuoteItem("In all things of nature there is something of the marvelous.", "Aristotle", "Neutral", "🙂")
        )

        val rv = findViewById<RecyclerView>(R.id.rv_quotes)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = QuoteAdapter(allQuotes) { q ->
            Toast.makeText(this, getString(R.string.quote_toast_copied, q.emoji), Toast.LENGTH_SHORT).show()
        }
        rv.adapter = adapter

        setupFilters()
        setupSearch()
    }

    private fun setupSearch() {
        val et = findViewById<TextInputEditText>(R.id.et_search)
        et.doOnTextChanged { text, _, _, _ ->
            currentFilter = text?.toString()?.trim().orEmpty()
            applyFilter()
        }
    }

    private fun setupFilters() {
        val cg = findViewById<ChipGroup>(R.id.chips_filter)

        fun addFilter(label: String, checked: Boolean = false) {
            val chip = Chip(this, null, com.google.android.material.R.style.Widget_Material3_Chip_Filter).apply {
                text = label
                isCheckable = true
                isChecked = checked
                isCheckedIconVisible = false
                setChipBackgroundColorResource(R.color.chip_bg_selector)
                setTextColor(resources.getColorStateList(R.color.chip_text_selector, theme))
                setChipStrokeColorResource(R.color.secondary_30_softgold)
                chipStrokeWidth = resources.getDimension(R.dimen.stroke_1dp)
                layoutParams = ChipGroup.LayoutParams(
                    ChipGroup.LayoutParams.WRAP_CONTENT,
                    ChipGroup.LayoutParams.WRAP_CONTENT
                )
            }
            cg.addView(chip)
        }

        val labels = listOf(getString(R.string.filter_all), "Happy", "Calm", "Energetic", "Sad", "Neutral")
        labels.forEachIndexed { index, s -> addFilter(s, checked = index == 0) }

        cg.setOnCheckedStateChangeListener { group, _ ->
            val id = group.checkedChipId
            val label = group.findViewById<Chip>(id)?.text?.toString() ?: getString(R.string.filter_all)
            applyFilter(labelIfChanged = label)
        }
    }

    private fun applyFilter(labelIfChanged: String? = null) {
        labelIfChanged?.let { currentMood = it }
        val query = currentFilter.lowercase()

        val base = if (currentMood == getString(R.string.filter_all)) {
            allQuotes
        } else {
            allQuotes.filter { it.mood.equals(currentMood, true) }
        }

        val filtered = if (query.isBlank()) {
            base
        } else {
            base.filter { it.text.lowercase().contains(query) || it.author.lowercase().contains(query) }
        }

        adapter.submit(filtered)
    }
}
