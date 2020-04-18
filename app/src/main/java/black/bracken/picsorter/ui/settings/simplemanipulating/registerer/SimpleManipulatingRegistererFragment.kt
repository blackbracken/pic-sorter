package black.bracken.picsorter.ui.settings.simplemanipulating.registerer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.SimpleManipulatingRegistererFragmentBinding
import black.bracken.picsorter.ext.observe
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.files.folderChooser
import kotlinx.android.synthetic.main.simple_manipulating_registerer_fragment.*
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

        buttonChangeDirectory.setOnClickListener { showDialogToChooseDirectory() }
        viewModel.directoryPath.observe(this) { textDirectoryPath.text = it }
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
