package black.bracken.picsorter.ui.settings.simplemanipulating.registerer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.SimpleManipulatingRegistererFragmentBinding
import black.bracken.picsorter.ext.observe
import black.bracken.picsorter.ext.setOnTextChanged
import black.bracken.picsorter.ui.settings.simplemanipulating.registerer.SimpleManipulatingRegistererViewModel.VerificationResult
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.files.folderChooser
import kotlinx.android.synthetic.main.simple_manipulating_registerer_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SimpleManipulatingRegistererFragment : Fragment() {

    private val viewModel by viewModel<SimpleManipulatingRegistererViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SimpleManipulatingRegistererFragmentBinding>(
            inflater, R.layout.simple_manipulating_registerer_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonRegister.setOnClickListener { onPressedRegisterButton() }
        buttonChangeDirectory.setOnClickListener { showDialogToChooseDirectory() }
        editDelaySeconds.setOnTextChanged { secondsText ->
            viewModel.secondsToDelete.value = secondsText.toIntOrNull()
        }
        switchToDeleteLater.setOnCheckedChangeListener { _, isEnabled ->
            viewModel.shouldDeleteLater.value = isEnabled
            editDelaySeconds.isEnabled = isEnabled
        }

        viewModel.directoryPath.observe(this) { textDirectoryPath.text = it }
    }

    private fun onPressedRegisterButton() = viewModel.viewModelScope.launch {
        when (viewModel.register()) {
            VerificationResult.SUCCEED -> {
                findNavController().navigate(R.id.action_simpleManipulatingRegistererFragment_to_simpleManipulatingSettingsFragment)
            }
            VerificationResult.MUST_NOT_EMPTY -> {
                Toast.makeText(
                    context ?: return@launch,
                    R.string.error_must_not_empty,
                    Toast.LENGTH_SHORT
                ).show()
            }
            VerificationResult.ALREADY_REGISTERED_UNDER_SAME_NAME -> {
                Toast.makeText(
                    context ?: return@launch,
                    R.string.error_duplication,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showDialogToChooseDirectory() {
        MaterialDialog(context ?: return).show {
            folderChooser(
                context,
                initialDirectory = File("/storage/emulated/0/"),
                allowFolderCreation = true
            ) { _, file ->
                viewModel.directoryPath.value = file.absolutePath
            }
        }
    }

}
