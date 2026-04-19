package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdc.vinilos.MainActivity
import com.tsdc.vinilos.R
import com.tsdc.vinilos.RoleAware
import com.tsdc.vinilos.UserRole
import com.tsdc.vinilos.databinding.FragmentAlbumsBinding

class AlbumsFragment : Fragment(), RoleAware {

    private var _b: FragmentAlbumsBinding? = null
    private val b get() = _b!!

    private lateinit var adapter: AlbumsAdapter

    // 🔥 LISTA MOCK (CON IMÁGENES REALES)
    private val mockAlbums = listOf(
        AlbumItem(
            1,
            "Poeta del pueblo",
            "Rubén Blades",
            "1984",
            "Salsa",
            "https://picsum.photos/id/237/600/400"
        ),
        AlbumItem(
            2,
            "A Night at the Opera",
            "Queen",
            "1975",
            "Rock",
            "https://picsum.photos/id/238/600/400"
        ),
        AlbumItem(
            3,
            "A Day at the Races",
            "Queen",
            "1976",
            "Rock",
            "https://picsum.photos/id/239/600/400"
        ),
        AlbumItem(
            4,
            "Thriller",
            "Michael Jackson",
            "1982",
            "Pop",
            "https://picsum.photos/id/240/600/400"
        ),
        AlbumItem(
            5,
            "Back in Black",
            "AC/DC",
            "1980",
            "Rock",
            "https://picsum.photos/id/241/600/400"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentAlbumsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔥 CONFIGURAR RECYCLER VIEW
        adapter = AlbumsAdapter(mockAlbums) { album ->
            val action =
                AlbumsFragmentDirections.actionAlbumsToDetail(album.id)
            findNavController().navigate(action)
        }

        b.rvAlbums.layoutManager = LinearLayoutManager(requireContext())
        b.rvAlbums.adapter = adapter

        // 🔥 OCULTAR TEXTO DE PLACEHOLDER
        b.tvPlaceholder.isVisible = false

        // 🔥 BOTÓN FAB → CREAR ALBUM
        b.fabAddAlbum.setOnClickListener {
            findNavController().navigate(R.id.action_albums_to_createAlbum)
        }

        updateForRole()
    }

    override fun onRoleChanged(role: UserRole) {
        updateForRole()
    }

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