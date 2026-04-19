package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdc.vinilos.MainActivity
import com.tsdc.vinilos.R
import com.tsdc.vinilos.RoleAware
import com.tsdc.vinilos.UserRole
import com.tsdc.vinilos.databinding.FragmentAlbumsBinding
import com.tsdc.vinilos.viewmodel.AlbumViewModel

class AlbumsFragment : Fragment(), RoleAware {

    private var _b: FragmentAlbumsBinding? = null
    private val b get() = _b!!

    private val viewModel: AlbumViewModel by viewModels()
    private lateinit var adapter: AlbumAdapter

    override fun onCreateView(
        i: LayoutInflater,
        c: ViewGroup?,
        s: Bundle?
    ) = FragmentAlbumsBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeViewModel()
        updateForRole()
        viewModel.refresh()
    }

    private fun setupRecyclerView() {
        adapter = AlbumAdapter { album ->
            findNavController().navigate(
                AlbumsFragmentDirections.actionAlbumsToDetail(album.id)
            )
        }
        b.rvAlbums.layoutManager = LinearLayoutManager(requireContext())
        b.rvAlbums.adapter = adapter
    }

    private fun setupFab() {
        b.fabAddAlbum.setOnClickListener {
            findNavController().navigate(R.id.action_albums_to_createAlbum)
        }
        b.btnRetry.setOnClickListener { viewModel.refresh() }
    }

    private fun observeViewModel() {
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            adapter.submitList(albums)
            b.rvAlbums.isVisible = albums.isNotEmpty()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressBar.isVisible = loading && adapter.itemCount == 0
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            b.tvError.isVisible = error != null
            b.tvError.text = error
            b.btnRetry.isVisible = error != null
        }
    }

    override fun onRoleChanged(role: UserRole) = updateForRole()

    private fun updateForRole() {
        val isColeccionista =
            (activity as? MainActivity)?.getCurrentRole() == UserRole.COLECCIONISTA
        b.fabAddAlbum.isVisible = isColeccionista
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}