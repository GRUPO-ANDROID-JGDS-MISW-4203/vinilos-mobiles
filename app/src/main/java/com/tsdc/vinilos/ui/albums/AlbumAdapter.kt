package com.tsdc.vinilos.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsdc.vinilos.databinding.ItemAlbumBinding
import com.tsdc.vinilos.model.Album

class AlbumAdapter(
    private val onClick: (Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.VH>() {

    private val items = mutableListOf<Album>()

    inner class VH(private val b: ItemAlbumBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(album: Album) {
            b.txtName.text = album.name
            b.txtArtistYear.text =
                "${album.performers.firstOrNull()?.name ?: "Desconocido"} · ${album.releaseDate.take(4)}"
            b.txtGenre.text = album.genre

            Glide.with(b.imgAlbum.context)
                .load(album.cover)
                .centerCrop()
                .into(b.imgAlbum)

            b.root.setOnClickListener { onClick(album) }
        }
    }

    fun submitList(data: List<Album>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}