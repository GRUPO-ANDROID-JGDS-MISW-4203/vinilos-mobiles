package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tsdc.vinilos.MainActivity
import com.tsdc.vinilos.R
import com.tsdc.vinilos.RoleAware
import com.tsdc.vinilos.UserRole
import com.tsdc.vinilos.databinding.FragmentAlbumDetailBinding
import com.tsdc.vinilos.viewmodel.AlbumViewModel

class AlbumDetailFragment : Fragment(), RoleAware {

    private var _b: FragmentAlbumDetailBinding? = null
    private val b get() = _b!!

    private val viewModel: AlbumViewModel by viewModels()
    private val args: AlbumDetailFragmentArgs by navArgs()
    private val trackAdapter = TrackAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        .also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.rvTracks.layoutManager = LinearLayoutManager(requireContext())
        b.rvTracks.adapter = trackAdapter

        b.btnAddTrack.setOnClickListener {
            // HU08 - Sprint 3
        }

        observeViewModel()
        updateForRole()
        viewModel.fetchAlbum(args.albumId)
    }

    private fun observeViewModel() {
        viewModel.album.observe(viewLifecycleOwner) { album ->
            b.tvAlbumTitle.text = album.name
            b.tvAlbumMeta.text = buildString {
                append(album.performers.firstOrNull()?.name ?: "Desconocido")
                append(" - ")
                append(album.releaseDate.take(4))
            }
            b.tvAlbumLabel.text = album.recordLabel
            b.chipGenre.text = album.genre
            b.tvDescription.text = album.description

            trackAdapter.submitList(album.tracks)

            Glide.with(this)
                .load(album.cover.ifEmpty { null })
                .placeholder(R.color.navy)
                .error(R.color.navy)
                .centerCrop()
                .into(b.ivAlbumCover)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            b.progressBar.isVisible = it
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            b.tvError.isVisible = error != null
            b.tvError.text = error
            b.btnRetry.isVisible = error != null
        }
    }

    override fun onRoleChanged(role: UserRole) = updateForRole()

    private fun updateForRole() {
        b.btnAddTrack.isVisible =
            (activity as? MainActivity)?.getCurrentRole() == UserRole.COLECCIONISTA
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}