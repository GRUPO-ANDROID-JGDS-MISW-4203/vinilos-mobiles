package com.tsdc.vinilos.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tsdc.vinilos.R
import com.tsdc.vinilos.databinding.FragmentArtistDetailBinding
import com.tsdc.vinilos.ui.albums.AlbumAdapter
import com.tsdc.vinilos.viewmodel.ArtistViewModel

class ArtistDetailFragment : Fragment() {

    private var _b: FragmentArtistDetailBinding? = null
    private val b get() = _b!!

    private val viewModel: ArtistViewModel by viewModels()
    private val args: ArtistDetailFragmentArgs by navArgs()
    private val albumAdapter = AlbumAdapter { album ->
        val action = ArtistDetailFragmentDirections.actionArtistDetailToAlbumDetail(album.id)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentArtistDetailBinding.inflate(inflater, container, false)
        .also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.rvAlbums.layoutManager = LinearLayoutManager(requireContext())
        b.rvAlbums.adapter = albumAdapter

        b.btnRetry.setOnClickListener {
            viewModel.fetchArtistDetail(args.artistId)
            viewModel.artist.value?.let { viewModel.fetchArtistAlbums(it.name) }
        }

        observeViewModel()
        viewModel.fetchArtistDetail(args.artistId)
    }

    private fun observeViewModel() {
        viewModel.artist.observe(viewLifecycleOwner) { artist ->
            if (artist == null) return@observe

            b.tvArtistName.text = artist.name
            b.tvArtistBio.text = artist.description

            Glide.with(this)
                .load(artist.image.ifEmpty { null })
                .placeholder(R.color.navy)
                .error(R.color.navy)
                .centerCrop()
                .into(b.imgArtist)

            android.util.Log.d("ArtistDetailFragment", "Fetching albums for artist: '${artist.name}' (id=${artist.id})")
            viewModel.fetchArtistAlbums(artist.name)
        }

        viewModel.artistAlbums.observe(viewLifecycleOwner) { albums ->
            albumAdapter.submitList(albums)
            b.tvNoAlbums.isVisible = albums.isEmpty()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressBar.isVisible = loading
            b.scrollView.isVisible = !loading
        }

        viewModel.albumsLoading.observe(viewLifecycleOwner) { loading ->
            b.progressAlbums.isVisible = loading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            b.tvError.isVisible = error != null
            b.tvError.text = error
            b.btnRetry.isVisible = error != null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
