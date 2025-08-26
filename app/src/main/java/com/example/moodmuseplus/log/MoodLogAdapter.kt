package com.example.moodmuseplus.log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moodmuseplus.R

class MoodLogAdapter(
    private val items: List<MoodEntryUi>,
    private val onClick: (MoodEntryUi) -> Unit
) : RecyclerView.Adapter<MoodLogAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvMood: TextView = itemView.findViewById(R.id.tv_mood)
        val tvNote: TextView = itemView.findViewById(R.id.tv_note)
        val tvEmoji: TextView = itemView.findViewById(R.id.tv_emoji)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mood_entry, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.tvDate.text = it.date
        holder.tvTime.text = it.time
        holder.tvMood.text = "${it.emoji} ${it.moodName}"
        holder.tvNote.text = it.note
        holder.tvEmoji.text = it.emoji

        holder.itemView.setOnClickListener { _ -> onClick(it) }
    }

    override fun getItemCount(): Int = items.size
}
