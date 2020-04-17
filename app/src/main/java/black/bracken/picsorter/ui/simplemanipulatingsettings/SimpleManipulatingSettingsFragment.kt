package black.bracken.picsorter.ui.simplemanipulatingsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.SimpleManipulatingSettingsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpleManipulatingSettingsFragment : Fragment() {

    private val viewModel by viewModel<SimpleManipulatingSettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SimpleManipulatingSettingsFragmentBinding>(
            inflater, R.layout.directories_chooser_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }



}