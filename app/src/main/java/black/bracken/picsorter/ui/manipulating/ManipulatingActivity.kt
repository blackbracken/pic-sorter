package black.bracken.picsorter.ui.manipulating

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.ActivityManipulatingBinding
import black.bracken.picsorter.ext.observe
import black.bracken.picsorter.ext.setOnTextChanged
import black.bracken.picsorter.service.model.SimpleManipulating
import coil.api.load
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.files.folderChooser
import com.afollestad.materialdialogs.list.listItems
import kotlinx.android.synthetic.main.activity_manipulating.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.File

class ManipulatingActivity : AppCompatActivity() {

    private val viewModel by inject<ManipulatingViewModel> {
        parametersOf(intent.getStringExtra(EXTRA_IMAGE_PATH))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipulating)

        DataBindingUtil.setContentView<ActivityManipulatingBinding>(
            this,
            R.layout.activity_manipulating
        ).also { binding -> binding.viewModel = viewModel }
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

        viewModel.imageDirectoryPath.observe(this) { path -> textDirectoryPath.text = path }
        buttonChangeDirectory.setOnClickListener { showDialogToChooseDirectory() }

        switchToDeleteLater.setOnCheckedChangeListener { _, isEnabled ->
            viewModel.shouldDeleteLater.value = isEnabled
            editDelaySeconds.isEnabled = isEnabled
        }
        editDelaySeconds.setOnTextChanged { secondsText ->
            viewModel.secondsToDelete.value = secondsText.toIntOrNull()
        }

        val shouldShowSimpleManipulatingDialog =
            intent.getBooleanExtra(EXTRA_OPEN_SIMPLE_MANIPULATING, false)
        if (shouldShowSimpleManipulatingDialog) {
            showDialogToOpenSimpleManipulating()
        }
    }

    private fun close() = finishAndRemoveTask()

    private fun showDialogToOpenSimpleManipulating() {
        viewModel.viewModelScope.launch {
            val manipulatings = viewModel.getAllSimpleManipulatings()

            MaterialDialog(this@ManipulatingActivity).show {
                listItems(items = manipulatings.map(SimpleManipulating::name)) { dialog, _, name ->
                    val manipulating = manipulatings.first { it.name == name }

                    viewModel.scheduleTask(manipulating)

                    dialog.cancel()
                    close()
                }
                negativeButton(R.string.dialog_cancel) { /* do nothing */ }
            }
        }
    }

    private fun showDialogToChooseDirectory() {
        MaterialDialog(this).show {
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