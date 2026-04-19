package com.tsdc.vinilos.ui.albums

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsdc.vinilos.databinding.ItemAlbumBinding

class AlbumsAdapter(
    private val items: List<AlbumItem>,
    private val onClick: (AlbumItem) -> Unit
) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AlbumItem) {
            binding.txtName.text = item.name
            binding.txtArtistYear.text = "${item.artist} · ${item.year}"
            binding.txtGenre.text = item.genre

            Glide.with(binding.imgAlbum.context)
                .load(item.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .centerCrop()
                .into(binding.imgAlbum)

            binding.root.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate().scaleX(0.98f).scaleY(0.98f).setDuration(120).start()
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(120).start()
                    }
                }
                false
            }

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}