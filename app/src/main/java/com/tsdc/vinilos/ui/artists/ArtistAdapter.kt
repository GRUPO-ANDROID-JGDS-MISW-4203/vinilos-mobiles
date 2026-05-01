package com.tsdc.vinilos.ui.artists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsdc.vinilos.R
import com.tsdc.vinilos.databinding.ItemArtistBinding
import com.tsdc.vinilos.model.Artist
import com.tsdc.vinilos.model.ArtistDateType

class ArtistAdapter(
    private val onClick: (Artist) -> Unit
) : RecyclerView.Adapter<ArtistAdapter.VH>() {

    private val items = mutableListOf<Artist>()

    inner class VH(private val b: ItemArtistBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(artist: Artist) {
            b.txtArtistName.text = artist.name
            b.txtArtistDescription.text = artist.description
            b.txtArtistDescription.isVisible = artist.description.isNotBlank()
            b.txtArtistDate.text = buildDateText(artist)
            b.txtArtistDate.isVisible = artist.date.isNotBlank()

            Glide.with(b.imgArtist.context)
                .load(artist.image.ifEmpty { null })
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(b.imgArtist)

            b.root.setOnClickListener { onClick(artist) }
        }

        private fun buildDateText(artist: Artist): String {
            val context = b.root.context
            return when (artist.dateType) {
                ArtistDateType.BIRTH -> context.getString(R.string.artist_birth_date, artist.date)
                ArtistDateType.CREATION -> context.getString(R.string.artist_creation_date, artist.date)
                ArtistDateType.UNKNOWN -> context.getString(R.string.artist_generic_date, artist.date)
            }
        }
    }

    fun submitList(data: List<Artist>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemArtistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}
