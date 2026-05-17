package com.tsdc.vinilos.ui.collectors

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdc.vinilos.databinding.FragmentCollectorDetailBinding
import com.tsdc.vinilos.ui.albums.AlbumAdapter
import com.tsdc.vinilos.ui.artists.ArtistAdapter
import com.tsdc.vinilos.viewmodel.CollectorViewModel

class CollectorDetailFragment : Fragment() {

    private var _b: FragmentCollectorDetailBinding? = null
    private val b get() = _b!!

    private val viewModel: CollectorViewModel by viewModels()
    private val args: CollectorDetailFragmentArgs by navArgs()

    private val albumAdapter = AlbumAdapter {}
    private val artistAdapter = ArtistAdapter {}

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentCollectorDetailBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeViewModel()
        viewModel.fetchCollector(args.collectorId)
    }

    private fun setupRecyclerViews() {
        b.rvFavoriteAlbums.layoutManager = LinearLayoutManager(requireContext())
        b.rvFavoriteAlbums.adapter = albumAdapter

        b.rvFavoriteArtists.layoutManager = LinearLayoutManager(requireContext())
        b.rvFavoriteArtists.adapter = artistAdapter
    }

    private fun observeViewModel() {
        viewModel.collector.observe(viewLifecycleOwner) { collector ->
            b.progressBar.isVisible = false

            val initials = collector.name
                .split(" ")
                .take(2)
                .joinToString("") { it.firstOrNull()?.toString() ?: "" }
                .uppercase()

            b.tvInitials.text = initials
            b.tvCollectorName.text = collector.name
            b.tvCollectorEmail.text = collector.email
            b.tvCollectorPhone.text = "📞 ${collector.telephone}"

            albumAdapter.submitList(collector.favoriteAlbums)
            artistAdapter.submitList(collector.favoriteArtists)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressBar.isVisible = loading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                b.progressBar.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}