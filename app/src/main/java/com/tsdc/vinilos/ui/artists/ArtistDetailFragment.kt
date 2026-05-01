package com.tsdc.vinilos.ui.artists
import android.os.Bundle; import android.view.*; import androidx.fragment.app.Fragment
import com.tsdc.vinilos.databinding.FragmentArtistDetailBinding
class ArtistDetailFragment : Fragment() {
    private var _b: FragmentArtistDetailBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) = FragmentArtistDetailBinding.inflate(i, c, false).also { _b = it }.root
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
