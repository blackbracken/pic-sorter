package black.bracken.picsorter.presentation.manipulating

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import black.bracken.picsorter.R
import black.bracken.picsorter.ext.setOnTextChanged
import black.bracken.picsorter.ext.startDirectoryChooserActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_manipulating.*
import net.rdrei.android.dirchooser.DirectoryChooserActivity
import java.io.File

/**
 * @author BlackBracken
 */
class ManipulatingActivity : AppCompatActivity(), ManipulatingContract.View {

    companion object {
        const val EXTRA_PICTURE_PATH = "PicturePath"

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

    private val manipulatedImage by lazy { File(intent.getStringExtra(EXTRA_PICTURE_PATH)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipulating)

        presenter.onStart()

        buttonApply.setOnClickListener { presenter.onApplyManipulation(manipulatedImage) }
        buttonTrash.setOnClickListener { presenter.onDismiss(manipulatedImage) }
        buttonChangeDirectory.setOnClickListener { presenter.onOpenDirectorySelector() }
        editNewName.setOnTextChanged { text -> presenter.onChangeNewName(text) }
    }

    override fun close() {
        finish()
    }

    override fun showManipulatedImage() {
        Glide
            .with(this)
            .load(manipulatedImage)
            .into(imageManipulated)
    }

    override fun openDirectorySelector() {
        startDirectoryChooserActivity(CALLBACK_OPEN_DIR_SELECTOR)
    }

    override fun enableToDeleteLater() = TODO("not implemented yet")

    override fun disableToDeleteLater() = TODO("not implemented yet")

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