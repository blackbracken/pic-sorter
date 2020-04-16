package black.bracken.picsorter.ui.dirchooser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.DirectoriesChooserFragmentBinding
import black.bracken.picsorter.ext.observe
import black.bracken.picsorter.ext.startDirectoryChooser
import com.afollestad.materialdialogs.MaterialDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.directories_chooser_fragment.*
import net.rdrei.android.dirchooser.DirectoryChooserActivity
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

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        recyclerDirectories.adapter = groupAdapter

        buttonAddDirectory.setOnClickListener { openDirectoryChooser() }

        viewModel.directoryPathObservedList.observe(this) { pathList ->
            with(groupAdapter) {
                clear()
                addAll(pathList.map { path ->
                    DirectoryItem(path) { showConfirmationToRemoveDirectory(path) }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CALLBACK_OPEN_DIR_CHOOSER) {
            val chosenPath = data?.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR)
                ?: return

            if (!viewModel.tryAddDirectory(chosenPath)) {
                showErrorDueToDuplication()
            }
        }
    }

    private fun openDirectoryChooser() {
        startDirectoryChooser(CALLBACK_OPEN_DIR_CHOOSER)
    }

    private fun showErrorDueToDuplication() {
        Toast
            .makeText(context, R.string.error_duplication, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showConfirmationToRemoveDirectory(directoryPath: String) {
        MaterialDialog(context ?: return).show {
            message(R.string.dialog_confirm_remove)
            positiveButton(R.string.dialog_do_remove) { viewModel.removeDirectory(directoryPath) }
            negativeButton(R.string.dialog_cancel) { /* do nothing */ }
        }
    }

    companion object {
        private const val CALLBACK_OPEN_DIR_CHOOSER = 2145
    }

}
