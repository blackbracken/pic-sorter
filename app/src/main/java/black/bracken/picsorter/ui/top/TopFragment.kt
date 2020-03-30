package black.bracken.picsorter.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.TopFragmentBinding
import black.bracken.picsorter.ext.observe
import black.bracken.picsorter.ui.AppActivity
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

        // TODO: remove below after the chooser is replaced by fragment.
        fun openDirectoriesChooser(): Unit =
            (activity as? AppActivity)?.openDirectoriesChooser() ?: Unit
        textDirectories.setOnClickListener { openDirectoriesChooser() }
        imageDirectoriesArrow.setOnClickListener { openDirectoriesChooser() }
    }

}