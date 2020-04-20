package black.bracken.picsorter.ui.manipulating

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.ActivityManipulatingBinding
import black.bracken.picsorter.ext.setOnTextChanged
import black.bracken.picsorter.ext.startDirectoryChooserActivity
import black.bracken.picsorter.service.model.SimpleManipulating
import coil.api.load
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import kotlinx.android.synthetic.main.activity_manipulating.*
import kotlinx.android.synthetic.main.item_directory.textDirectoryPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.rdrei.android.dirchooser.DirectoryChooserActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

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

        buttonChangeDirectory.setOnClickListener {
            startDirectoryChooserActivity(CALLBACK_OPEN_DIR_SELECTOR)
        }

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
            showOpenManipulatingDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CALLBACK_OPEN_DIR_SELECTOR) {
            val path = data?.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR) ?: return

            textDirectoryPath.text = path
        }
    }

    private fun close() = finishAndRemoveTask()

    private fun showOpenManipulatingDialog() = CoroutineScope(Dispatchers.Main).launch {
        val manipulatings = viewModel.getAllSimpleManipulatings()

        MaterialDialog(this@ManipulatingActivity).show {
            listItems(items = manipulatings.map(SimpleManipulating::name)) { dialog, _, name ->
                val manipulating = manipulatings.first { it.name == name }

                viewModel.scheduleTask(manipulating)
                dialog.cancel()
                close()
            }
            negativeButton(R.string.cancel_label) { /* do nothing */ }
        }
    }

    companion object {
        const val EXTRA_IMAGE_PATH = "ImagePath"
        const val EXTRA_OPEN_SIMPLE_MANIPULATING = "OpenSimpleManipulating"

        private const val CALLBACK_OPEN_DIR_SELECTOR = 2045
    }

}