package black.bracken.picsorter.manipulating.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import black.bracken.picsorter.data.model.SimpleManipulating
import black.bracken.picsorter.feature_common.ext.observeOnce
import black.bracken.picsorter.feature_common.ext.setOnTextChanged
import black.bracken.picsorter.manipulating.R
import black.bracken.picsorter.manipulating.databinding.ManipulatingFragmentBinding
import coil.api.load
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.files.folderChooser
import com.afollestad.materialdialogs.list.listItems
import kotlinx.android.synthetic.main.manipulating_fragment.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.File

class ManipulatingFragment : Fragment() {

    private val viewModel by inject<ManipulatingViewModel> {
        parametersOf(requireActivity().intent.getStringExtra(EXTRA_IMAGE_PATH))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ManipulatingFragmentBinding>(
            inflater, R.layout.manipulating_fragment, container, false
        ).also { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageManipulated.load(viewModel.image)

        viewModel.removeNotificationIfExists()

        buttonApply.setOnClickListener {
            viewModel.manipulate()
            close()
        }
        buttonTrash.setOnClickListener {
            viewModel.dump()
            close()
        }

        viewModel.imageDirectoryPath.observe(viewLifecycleOwner) { path ->
            textDirectoryPath.text = path
        }
        buttonChangeDirectory.setOnClickListener { showDialogToChooseDirectory() }

        switchToDeleteLater.setOnCheckedChangeListener { _, isEnabled ->
            viewModel.shouldDeleteLater.value = isEnabled
            editDelaySeconds.isEnabled = isEnabled
        }
        editDelaySeconds.setOnTextChanged { secondsText ->
            viewModel.secondsToDelete.value = secondsText.toIntOrNull()
        }

        val shouldShowSimpleManipulatingDialog = requireActivity().intent.getBooleanExtra(
            EXTRA_OPEN_SIMPLE_MANIPULATING, false
        )
        if (shouldShowSimpleManipulatingDialog) {
            viewModel.simpleManipulatings.observeOnce(this) { manipulatings ->
                showDialogToOpenSimpleManipulating(manipulatings)
            }
        }
    }

    private fun close() = activity?.finishAndRemoveTask()

    private fun showDialogToOpenSimpleManipulating(manipulatings: List<SimpleManipulating>) {
        MaterialDialog(requireContext()).show {
            listItems(items = manipulatings.map(SimpleManipulating::name)) { dialog, _, name ->
                val manipulating = manipulatings.first { it.name == name }

                viewModel.scheduleTask(manipulating)

                dialog.cancel()
                close()
            }
            negativeButton(R.string.dialog_cancel) { /* do nothing */ }
        }
    }

    private fun showDialogToChooseDirectory() {
        MaterialDialog(requireContext()).show {
            folderChooser(
                context,
                initialDirectory = File("/storage/emulated/0/"),
                allowFolderCreation = true
            ) { _, file ->
                viewModel.imageDirectoryPath.value = file.absolutePath
            }
        }
    }

    companion object {
        const val EXTRA_IMAGE_PATH = "ImagePath"
        const val EXTRA_OPEN_SIMPLE_MANIPULATING = "OpenSimpleManipulating"
    }

}