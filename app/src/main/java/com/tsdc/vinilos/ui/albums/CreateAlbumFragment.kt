package com.tsdc.vinilos.ui.albums
import android.os.Bundle; import android.view.*; import androidx.fragment.app.Fragment
import com.tsdc.vinilos.databinding.FragmentCreateAlbumBinding
class CreateAlbumFragment : Fragment() {
    private var _b: FragmentCreateAlbumBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) = FragmentCreateAlbumBinding.inflate(i, c, false).also { _b = it }.root
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
