package com.tsdc.vinilos.ui.collectors

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsdc.vinilos.databinding.ItemCollectorBinding
import com.tsdc.vinilos.model.Collector

class CollectorAdapter(
    private val onClick: (Collector) -> Unit
) : ListAdapter<Collector, CollectorAdapter.VH>(DiffCallback) {

    inner class VH(val b: ItemCollectorBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(collector: Collector) {

            // Generar iniciales a partir del nombre
            val initials = collector.name
                .split(" ")
                .take(2)
                .joinToString("") { it.firstOrNull()?.toString() ?: "" }
                .uppercase()

            b.tvInitials.text         = initials
            b.tvCollectorName.text    = collector.name
            b.tvCollectorEmail.text   = collector.email
            b.tvCollectorAlbums.text  = when (val n = collector.favoriteAlbums.size) {
                0    -> "Sin álbumes registrados"
                1    -> "1 álbum favorito"
                else -> "$n álbumes favoritos"
            }

            b.root.setOnClickListener { onClick(collector) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCollectorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<Collector>() {
        override fun areItemsTheSame(a: Collector, b: Collector) = a.id == b.id
        override fun areContentsTheSame(a: Collector, b: Collector) = a == b
    }
}