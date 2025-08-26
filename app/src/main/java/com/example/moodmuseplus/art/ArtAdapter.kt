package com.example.moodmuseplus.art

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moodmuseplus.R

class ArtAdapter(
    private var items: List<ArtItem>,
    private val onClick: (ArtItem) -> Unit
) : RecyclerView.Adapter<ArtAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.iv_art)
        val title: TextView = v.findViewById(R.id.tv_title)
        val mood: TextView = v.findViewById(R.id.tv_mood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_art, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        h.title.text = item.title
        h.mood.text = "${item.emoji} ${item.mood}"

        // Try to load the drawable by name; fallback to placeholder if not found
        val ctx = h.itemView.context
        val resId = ctx.resources.getIdentifier(item.imageName, "drawable", ctx.packageName)
        if (resId != 0) {
            h.image.setImageResource(resId)
        } else {
            h.image.setImageResource(R.drawable.bg_art_placeholder)
        }

        h.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun submit(newItems: List<ArtItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
