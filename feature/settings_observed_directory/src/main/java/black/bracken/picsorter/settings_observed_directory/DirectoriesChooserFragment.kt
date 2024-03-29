package black.bracken.picsorter.settings_observed_directory

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import black.bracken.picsorter.feature_common.ext.createIntentForExternalStoragePermission
import black.bracken.picsorter.feature_common.ext.hasExternalStoragePermission
import black.bracken.picsorter.settings_observed_directory.databinding.DirectoriesChooserFragmentBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.files.folderChooser
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.directories_chooser_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class DirectoriesChooserFragment : Fragment() {

    private val viewModel by viewModel<DirectoriesChooserViewModel>()

    private val requestPermissionToOpenDirChooserIntent = createIntentForExternalStoragePermission {
        showDialogToChooseDirectory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DirectoriesChooserFragmentBinding>(
            inflater, R.layout.directories_chooser_fragment, container, false
        ).also { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbarDirectories)
        toolbarDirectories.setTitle(R.string.title_directories_chooser)

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        recyclerDirectories.adapter = groupAdapter

        buttonAddDirectory.setOnClickListener {
            if (hasExternalStoragePermission()) {
                showDialogToChooseDirectory()
            } else {
                requestPermissionToOpenDirChooserIntent.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        viewModel.directoryPathObservedList.observe(viewLifecycleOwner) { pathList ->
            with(groupAdapter) {
                clear()
                addAll(pathList.map { path ->
                    DirectoryItem(
                        path
                    ) { showConfirmationToRemoveDirectory(path) }
                })
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
                if (!viewModel.tryAddDirectory(file.absolutePath)) {
                    Toast
                        .makeText(context, R.string.error_duplication, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showConfirmationToRemoveDirectory(directoryPath: String) {
        MaterialDialog(context ?: return).show {
            message(R.string.dialog_confirm_remove)
            positiveButton(R.string.dialog_do_remove) { viewModel.removeDirectory(directoryPath) }
            negativeButton(R.string.dialog_cancel) { /* do nothing */ }
        }
    }

}
