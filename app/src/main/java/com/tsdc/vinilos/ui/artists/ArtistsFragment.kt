package com.tsdc.vinilos.ui.artists
import android.os.Bundle; import android.view.*; import androidx.fragment.app.Fragment
import com.tsdc.vinilos.databinding.FragmentArtistsBinding
class ArtistsFragment : Fragment() {
    private var _b: FragmentArtistsBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) = FragmentArtistsBinding.inflate(i, c, false).also { _b = it }.root
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
