package black.bracken.picsorter.presentation.settings.directorieschooser

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import black.bracken.picsorter.R
import black.bracken.picsorter.ext.startDirectoryChooserActivity
import black.bracken.picsorter.presentation.settings.directorieschooser.recycler.DirectoryRecyclerAdapter
import kotlinx.android.synthetic.main.activity_settings_directories_chooser.*
import net.rdrei.android.dirchooser.DirectoryChooserActivity

/**
 * @author BlackBracken
 */
class SettingsDirectoriesChooserActivity
    : AppCompatActivity(), SettingsDirectoriesChooserContract.View {

    companion object {
        private const val CALLBACK_OPEN_DIR_CHOOSER = 2145
    }

    override val presenter by lazy { SettingsDirectoriesChooserPresenter(this, this) }

    private val recyclerAdapter by lazy { DirectoryRecyclerAdapter(this, presenter::onTryToRemoveDirectory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_directories_chooser)

        setSupportActionBar(toolbarDirectories)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setTitle(R.string.layout_settings_open_chooser)
        }

        recyclerDirectories.adapter = recyclerAdapter
        recyclerDirectories.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        buttonAddDirectory.setOnClickListener { presenter.onOpenDirectoryChooser() }

        presenter.onStart()
    }

    override fun addDirectory(directoryPath: String) {
        recyclerAdapter += directoryPath
    }

    override fun removeDirectory(directoryPath: String) {
        recyclerAdapter -= directoryPath
    }

    override fun openDirectoryChooser() {
        startDirectoryChooserActivity(CALLBACK_OPEN_DIR_CHOOSER)
    }

    override fun showConfirmationToRemoveDirectory(directoryPath: String) {
        AlertDialog.Builder(this)
            .setMessage(R.string.dialog_confirm_remove)
            .setPositiveButton(R.string.dialog_do_remove) { _, _ -> presenter.onRemoveDirectory(directoryPath) }
            .setNegativeButton(R.string.dialog_cancel) { _, _ -> /* do nothing */ }
            .create()
            .show()
    }

    override fun showErrorDueToDuplication() {
        Toast
            .makeText(this, R.string.error_duplication, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CALLBACK_OPEN_DIR_CHOOSER -> {
                data?.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR)?.run(presenter::onAddDirectory)
            }
        }
    }

}