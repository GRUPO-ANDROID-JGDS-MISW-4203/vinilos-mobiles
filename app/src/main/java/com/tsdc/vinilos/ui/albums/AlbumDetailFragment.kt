package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tsdc.vinilos.R
import com.tsdc.vinilos.databinding.FragmentAlbumDetailBinding

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumId = arguments?.getInt("albumId", 0) ?: 0

        binding.progressBar.visibility = View.VISIBLE

        try {
            when (albumId) {

                1 -> {
                    loadAlbum(
                        name = "Poeta del pueblo",
                        genre = "Salsa",
                        date = "1984",
                        label = "Fania",
                        description = "Álbum representativo de Rubén Blades con contenido social.",
                        tracks = "1. Track 1\n2. Track 2\n3. Track 3",
                        artists = "Rubén Blades",
                        image = "https://picsum.photos/id/237/600/400"
                    )
                }

                2 -> {
                    loadAlbum(
                        name = "A Night at the Opera",
                        genre = "Rock",
                        date = "1975",
                        label = "EMI",
                        description = "Uno de los álbumes más emblemáticos de Queen.",
                        tracks = "1. Bohemian Rhapsody\n2. Love of My Life\n3. You're My Best Friend",
                        artists = "Queen",
                        image = "https://picsum.photos/id/238/600/400"
                    )
                }

                3 -> {
                    loadAlbum(
                        name = "A Day at the Races",
                        genre = "Rock",
                        date = "1976",
                        label = "EMI",
                        description = "Continuación del sonido clásico de Queen.",
                        tracks = "1. Somebody to Love\n2. Tie Your Mother Down",
                        artists = "Queen",
                        image = "https://picsum.photos/id/239/600/400"
                    )
                }

                else -> {
                    loadAlbum(
                        name = "Thriller",
                        genre = "Pop",
                        date = "1982",
                        label = "Epic",
                        description = "Uno de los discos más vendidos de la historia.",
                        tracks = "1. Thriller\n2. Beat It\n3. Billie Jean",
                        artists = "Michael Jackson",
                        image = "https://picsum.photos/id/240/600/400"
                    )
                }
            }

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error cargando álbum", Toast.LENGTH_SHORT).show()
        } finally {
            binding.progressBar.visibility = View.GONE
        }

        // 🔥 BOTÓN AGREGAR TRACK
        binding.btnAddTrack.setOnClickListener {
            findNavController().navigate(
                R.id.action_detail_to_addTrack,
                Bundle().apply { putInt("albumId", albumId) }
            )
        }

        // 🔙 BOTÓN ATRÁS DEL CELULAR
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }

    // 🔥 FUNCIÓN PARA CARGAR DATOS
    private fun loadAlbum(
        name: String,
        genre: String,
        date: String,
        label: String,
        description: String,
        tracks: String,
        artists: String,
        image: String
    ) {
        binding.txtName.text = name
        binding.txtGenre.text = "Género: $genre"
        binding.txtRelease.text = "Fecha: $date"
        binding.txtLabel.text = "Sello: $label"
        binding.txtDescription.text = description
        binding.txtTracks.text = tracks
        binding.txtArtists.text = artists

        Glide.with(requireContext())
            .load(image)
            .centerCrop()
            .into(binding.imgAlbum)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}