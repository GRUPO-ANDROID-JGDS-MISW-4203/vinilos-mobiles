package com.tsdc.vinilos.ui.albums

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsdc.vinilos.R
import com.tsdc.vinilos.databinding.ItemAlbumBinding
import com.tsdc.vinilos.model.Album

class AlbumAdapter(
    private val onClick: (Album) -> Unit
) : ListAdapter<Album, AlbumAdapter.VH>(DiffCallback) {

    inner class VH(val b: ItemAlbumBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(album: Album) {
            b.tvAlbumName.text = album.name
            b.tvAlbumArtistYear.text = buildString {
                append(album.performers.firstOrNull()?.name ?: "Artista desconocido")
                append(" · ")
                append(album.releaseDate.take(4))
            }
            b.chipGenre.text = album.genre

            Glide.with(b.root)
                .load(album.cover.ifEmpty { null })
                .placeholder(R.color.navy)
                .error(R.color.navy)
                .centerCrop()
                .into(b.ivAlbumCover)

            b.root.setOnClickListener { onClick(album) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(a: Album, b: Album) = a.id == b.id
        override fun areContentsTheSame(a: Album, b: Album) = a == b
    }
}