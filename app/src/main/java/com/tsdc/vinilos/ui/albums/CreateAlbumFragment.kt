package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tsdc.vinilos.databinding.FragmentCreateAlbumBinding
import com.tsdc.vinilos.model.AlbumRequest
import com.tsdc.vinilos.viewmodel.AlbumViewModel

class CreateAlbumFragment : Fragment() {

    private var _b: FragmentCreateAlbumBinding? = null
    private val b get() = _b!!

    private val viewModel: AlbumViewModel by viewModels()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentCreateAlbumBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        observeViewModel()
    }

    private fun setupButtons() {
        b.btnSave.setOnClickListener { validateAndSubmit() }
        b.btnCancel.setOnClickListener { findNavController().popBackStack() }
    }

    private fun validateAndSubmit() {

        val name = b.etName.text.toString().trim()
        val cover = b.etCover.text.toString().trim()
        val date = b.etDate.text.toString().trim()
        val label = b.etLabel.text.toString().trim()
        val genre = b.spinnerGenre.selectedItem.toString()
        val desc = b.etDescription.text.toString().trim()

        if (name.isEmpty()) {
            b.tilName.error = "El nombre es obligatorio"
            return
        }

        if (cover.isEmpty()) {
            b.tilCover.error = "La URL de portada es obligatoria"
            return
        }

        if (date.isEmpty()) {
            b.tilDate.error = "La fecha es obligatoria"
            return
        }

        if (label.isEmpty()) {
            b.tilLabel.error = "El sello es obligatorio"
            return
        }

        b.tilName.error = null
        b.tilCover.error = null
        b.tilDate.error = null
        b.tilLabel.error = null

        viewModel.createAlbum(
            AlbumRequest(
                name,
                cover,
                date,
                genre,
                label,
                desc
            )
        )
    }

    private fun observeViewModel() {

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressBar.isVisible = loading
            b.btnSave.isEnabled = !loading
        }

        viewModel.success.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}