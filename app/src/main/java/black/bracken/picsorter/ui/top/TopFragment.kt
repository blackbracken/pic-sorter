package black.bracken.picsorter.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.TopFragmentBinding
import black.bracken.picsorter.ext.observe
import kotlinx.android.synthetic.main.top_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class TopFragment : Fragment() {

    private val viewModel by viewModel<TopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<TopFragmentBinding>(
            inflater, R.layout.top_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.enablesObserver.observe(this) { isChecked ->
            viewModel.switchToEnableImageObserver(isChecked)
        }
        viewModel.runsOnBoot.observe(this) { isChecked ->
            viewModel.switchToRunOnBoot(isChecked)
        }

        fun navigateDirChooser() =
            findNavController().navigate(R.id.action_topFragment_to_directoriesChooserFragment)
        textDirectories.setOnClickListener { navigateDirChooser() }
        imageDirectoriesArrow.setOnClickListener { navigateDirChooser() }
    }

}