package com.tsdc.vinilos.ui.collectors

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdc.vinilos.databinding.FragmentCollectorsBinding
import com.tsdc.vinilos.viewmodel.CollectorViewModel

class CollectorsFragment : Fragment() {

    private var _b: FragmentCollectorsBinding? = null
    private val b get() = _b!!

    private val viewModel: CollectorViewModel by viewModels()
    private lateinit var adapter: CollectorAdapter

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentCollectorsBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButtons()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = CollectorAdapter { _ ->
            // Navegar al detalle — HU06
            // findNavController().navigate(
            //     CollectorsFragmentDirections.actionCollectorsToDetail(it.id)
            // )
        }
        b.rvCollectors.layoutManager = LinearLayoutManager(requireContext())
        b.rvCollectors.adapter = adapter
    }

    private fun setupButtons() {
        b.btnRetry.setOnClickListener { viewModel.refresh() }
    }

    private fun observeViewModel() {
        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            adapter.submitList(collectors)
            b.rvCollectors.isVisible = collectors.isNotEmpty()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressBar.isVisible = loading && adapter.itemCount == 0
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            b.tvError.isVisible  = error != null
            b.tvError.text       = error
            b.btnRetry.isVisible = error != null
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}