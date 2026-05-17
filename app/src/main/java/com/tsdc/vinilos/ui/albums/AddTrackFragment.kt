package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tsdc.vinilos.databinding.FragmentAddTrackBinding
import com.tsdc.vinilos.model.TrackRequest
import com.tsdc.vinilos.viewmodel.AlbumViewModel

class AddTrackFragment : Fragment() {

    private var _b: FragmentAddTrackBinding? = null
    private val b get() = _b!!

    private val viewModel: AlbumViewModel by viewModels()
    private val args: AddTrackFragmentArgs by navArgs()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentAddTrackBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCurrentTracks()
        setupButtons()
        observeViewModel()
    }

    private fun loadCurrentTracks() {
        viewModel.fetchAlbum(args.albumId)

        viewModel.album.observe(viewLifecycleOwner) { album ->
            b.tvCurrentTracks.text =
                if (album.tracks.isEmpty()) {
                    "Sin tracks aún"
                } else {
                    album.tracks.mapIndexed { i, t ->
                        "${i + 1}. ${t.name}  ${t.duration}"
                    }.joinToString("\n")
                }
        }
    }

    private fun setupButtons() {
        b.btnAdd.setOnClickListener { validateAndSubmit() }

        b.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateAndSubmit() {

        val name = b.etTrackName.text.toString().trim()
        val duration = b.etDuration.text.toString().trim()
        val videoUrl = b.etVideoUrl.text.toString().trim()

        if (name.isEmpty()) {
            b.tilTrackName.error = "El nombre es obligatorio"
            return
        }

        if (duration.isEmpty()) {
            b.tilDuration.error = "La duración es obligatoria"
            return
        }

        if (!duration.matches(Regex("\\d{1,2}:\\d{2}"))) {
            b.tilDuration.error = "Formato inválido. Usa mm:ss"
            return
        }

        b.tilTrackName.error = null
        b.tilDuration.error = null

        viewModel.addTrack(
            args.albumId,
            TrackRequest(name, duration, videoUrl)
        )
    }

    private fun observeViewModel() {

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressBar.isVisible = loading
            b.btnAdd.isEnabled = !loading
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