package black.bracken.picsorter.presentation.manipulating

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import black.bracken.picsorter.R
import black.bracken.picsorter.ext.setOnTextChanged
import black.bracken.picsorter.ext.startDirectoryChooserActivity
import coil.api.load
import kotlinx.android.synthetic.main.activity_manipulating.*
import net.rdrei.android.dirchooser.DirectoryChooserActivity
import java.io.File

class ManipulatingActivity : AppCompatActivity(), ManipulatingContract.View {

    companion object {
        const val EXTRA_IMAGE_PATH = "ImagePath"

        private const val CALLBACK_OPEN_DIR_SELECTOR = 2045
    }

    override val presenter by lazy { ManipulatingPresenter(this, this, manipulatedImage) }

    override var directoryPathText: String
        get() = textDirectoryPath.text.toString()
        set(value) {
            textDirectoryPath.text = value
        }

    override var newNameHint: String
        get() = editNewName.hint.toString()
        set(value) {
            editNewName.hint = value
        }

    override var imageExtension: String
        get() = textExtension.text.toString().drop(1)
        set(value) {
            @SuppressLint("SetTextI18n")
            textExtension.text = ".$value"
        }

    private val manipulatedImage by lazy { File(intent.getStringExtra(EXTRA_IMAGE_PATH)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipulating)

        presenter.onStart()

        buttonApply.setOnClickListener { presenter.onApplyManipulation(manipulatedImage) }
        buttonTrash.setOnClickListener { presenter.onDismiss(manipulatedImage) }
        buttonChangeDirectory.setOnClickListener { presenter.onOpenDirectorySelector() }
        switchToDeleteLater.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onEnableToDeleteLater(editDelaySeconds.text.toString().toIntOrNull())
            } else {
                presenter.onDisableToDeleteLater()
            }
        }
        editNewName.setOnTextChanged { text -> presenter.onChangeNewName(text) }
        editDelaySeconds.setOnTextChanged { secondsText ->
            presenter.onChangeDelayToDelete(
                secondsText.toIntOrNull()
            )
        }
    }

    override fun close() {
        finishAndRemoveTask()
    }

    override fun showManipulatedImage() {
        imageManipulated.load(manipulatedImage.absoluteFile)
    }

    override fun openDirectorySelector() {
        startDirectoryChooserActivity(CALLBACK_OPEN_DIR_SELECTOR)
    }

    override fun enableDelayEdit() {
        editDelaySeconds.isEnabled = true
    }

    override fun disableDelayEdit() {
        editDelaySeconds.isEnabled = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CALLBACK_OPEN_DIR_SELECTOR -> {
                data?.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR)
                    ?.let { path -> File(path) }
                    ?.run(presenter::onChangeDirectory)
            }
        }
    }

}