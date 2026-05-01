package com.tsdc.vinilos.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdc.vinilos.databinding.FragmentArtistsBinding
import com.tsdc.vinilos.viewmodel.ArtistViewModel

class ArtistsFragment : Fragment() {

    private var _b: FragmentArtistsBinding? = null
    private val b get() = _b!!

    private val viewModel: ArtistViewModel by viewModels()
    private lateinit var adapter: ArtistAdapter

    override fun onCreateView(
        i: LayoutInflater,
        c: ViewGroup?,
        s: Bundle?
    ) = FragmentArtistsBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRetry()
        observeViewModel()
        viewModel.refresh()
    }

    private fun setupRecyclerView() {
        adapter = ArtistAdapter { artist ->
            findNavController().navigate(
                ArtistsFragmentDirections.actionArtistsToDetail(artist.id)
            )
        }
        b.rvArtists.layoutManager = LinearLayoutManager(requireContext())
        b.rvArtists.adapter = adapter
    }

    private fun setupRetry() {
        b.btnRetryArtists.setOnClickListener { viewModel.refresh() }
    }

    private fun observeViewModel() {
        viewModel.artists.observe(viewLifecycleOwner) { artists ->
            adapter.submitList(artists)
            renderEmptyState()
            b.rvArtists.isVisible = artists.isNotEmpty()
            b.tvArtistsFooter.isVisible = artists.isNotEmpty()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressBarArtists.isVisible = loading && adapter.itemCount == 0
            renderEmptyState()
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            val hasError = error != null
            b.tvArtistsError.isVisible = hasError
            b.tvArtistsError.text = error
            b.btnRetryArtists.isVisible = hasError
            if (hasError) {
                b.tvArtistsFooter.isVisible = false
            }
            renderEmptyState()
        }
    }

    private fun renderEmptyState() {
        b.tvArtistsEmpty.isVisible = adapter.itemCount == 0 &&
            viewModel.isLoading.value != true &&
            viewModel.error.value == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
