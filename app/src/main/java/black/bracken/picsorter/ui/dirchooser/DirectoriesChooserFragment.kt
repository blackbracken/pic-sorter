package black.bracken.picsorter.ui.dirchooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.DirectoriesChooserFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DirectoriesChooserFragment : Fragment() {
    private val viewModel by viewModel<DirectoriesChooserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DirectoriesChooserFragmentBinding>(
            inflater, R.layout.directories_chooser_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
