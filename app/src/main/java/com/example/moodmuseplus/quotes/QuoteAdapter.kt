package com.example.moodmuseplus.quotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moodmuseplus.R

class QuoteAdapter(
    private var items: List<QuoteItem>,
    private val onClick: (QuoteItem) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: ImageView = v.findViewById(R.id.iv_quote_icon)
        val text: TextView = v.findViewById(R.id.tv_text)
        val author: TextView = v.findViewById(R.id.tv_author)
        val mood: TextView = v.findViewById(R.id.tv_mood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        h.text.text = "“${item.text}”"
        h.author.text = "— ${item.author}"
        h.mood.text = "${item.emoji} ${item.mood}"
        h.icon.setImageResource(R.drawable.ic_quote)
        h.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun submit(newItems: List<QuoteItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
