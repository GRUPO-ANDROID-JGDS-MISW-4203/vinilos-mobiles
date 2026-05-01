package com.tsdc.vinilos.ui.collectors
import android.os.Bundle; import android.view.*; import androidx.fragment.app.Fragment
import com.tsdc.vinilos.databinding.FragmentCollectorsBinding
class CollectorsFragment : Fragment() {
    private var _b: FragmentCollectorsBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) = FragmentCollectorsBinding.inflate(i, c, false).also { _b = it }.root
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
