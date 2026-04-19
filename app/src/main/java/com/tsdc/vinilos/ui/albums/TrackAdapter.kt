package com.tsdc.vinilos.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsdc.vinilos.databinding.ItemTrackBinding
import com.tsdc.vinilos.model.Track

class TrackAdapter : RecyclerView.Adapter<TrackAdapter.VH>() {

    private val items = mutableListOf<Track>()

    inner class VH(private val b: ItemTrackBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(track: Track, position: Int) {
            b.tvTrackNumber.text = "${position + 1}"
            b.tvTrackName.text = track.name
            b.tvTrackDuration.text = track.duration
        }
    }

    fun submitList(data: List<Track>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}