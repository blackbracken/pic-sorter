package black.bracken.picsorter.ui.settings.simplemanipulating.registerer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import org.koin.android.ext.android.inject

class SimpleManipulatingRegistererFragment : Fragment() {

    private val viewModel by inject<SimpleManipulatingRegistererViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.simple_manipulating_registerer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
