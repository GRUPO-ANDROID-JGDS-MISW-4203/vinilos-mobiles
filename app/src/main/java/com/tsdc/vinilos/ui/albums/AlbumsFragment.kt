package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tsdc.vinilos.MainActivity
import com.tsdc.vinilos.RoleAware
import com.tsdc.vinilos.UserRole
import com.tsdc.vinilos.databinding.FragmentAlbumsBinding

class AlbumsFragment : Fragment(), RoleAware {
    private var _b: FragmentAlbumsBinding? = null
    private val b get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentAlbumsBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateForRole()
    }

    override fun onRoleChanged(role: UserRole) = updateForRole()

    private fun updateForRole() {
        val isColeccionista = (activity as? MainActivity)?.getCurrentRole() == UserRole.COLECCIONISTA
        // FAB solo visible para coleccionistas (HU07)
        b.fabAddAlbum.isVisible = isColeccionista
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
