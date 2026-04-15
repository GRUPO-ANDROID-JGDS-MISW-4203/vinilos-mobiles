package com.tsdc.vinilos.ui.albums
import android.os.Bundle; import android.view.*; import androidx.fragment.app.Fragment
import com.tsdc.vinilos.databinding.FragmentAlbumDetailBinding
class AlbumDetailFragment : Fragment() {
    private var _b: FragmentAlbumDetailBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) = FragmentAlbumDetailBinding.inflate(i, c, false).also { _b = it }.root
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
