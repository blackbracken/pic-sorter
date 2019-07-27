package black.bracken.picsorter.presentation.settings

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.ext.startDirectoryChooserActivity
import kotlinx.android.synthetic.main.activity_settings.*
import net.rdrei.android.dirchooser.DirectoryChooserActivity

/**
 * @author BlackBracken
 */
class SettingsActivity : AppCompatActivity(), SettingsContract.View {

    companion object {
        private const val CALLBACK_OPEN_DIR_SELECTOR = 1945
    }

    override val presenter: SettingsContract.Presenter by lazy { SettingsPresenter(this, this) }

    private val observedPathListAdapter by lazy { ObservedDirectoryPathListAdapter(this, listObserved, presenter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        // TODO: notice to agree permission if not
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

        switchEnable.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleObserverService(isChecked) }
        switchRunOnBoot.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleRunOnBoot(isChecked) }
        buttonAddObserved.setOnClickListener { presenter.onOpenObservedPathSelector() }

        listObserved.adapter = observedPathListAdapter

        presenter.onStart()
    }

    override fun switchOnToEnableObserver() {
        switchEnable.isChecked = true
    }

    override fun switchOffToEnableObserver() {
        switchEnable.isChecked = false
    }

    override fun switchOnToRunOnBoot() {
        switchRunOnBoot.isChecked = true
    }

    override fun switchOffToRunOnBoot() {
        switchRunOnBoot.isChecked = false
    }

    override fun openObservedPathSelector() {
        startDirectoryChooserActivity(CALLBACK_OPEN_DIR_SELECTOR)
    }

    override fun addObservedPath(path: String) {
        observedPathListAdapter.observedPathList += path
        observedPathListAdapter.notifyDataSetChanged()
    }

    override fun removeObservedPath(path: String) {
        observedPathListAdapter.observedPathList -= path
        observedPathListAdapter.notifyDataSetChanged()
    }

    override fun showErrorDueToDuplication() {
        Toast
            .makeText(this, R.string.error_duplication, Toast.LENGTH_SHORT)
            .show()
    }

    override fun showConfirmDialogToRemoveObserved(path: String) {
        AlertDialog.Builder(this)
            .setMessage(R.string.dialog_confirm_remove)
            .setPositiveButton(R.string.dialog_do_remove) { _, _ -> presenter.onRemoveObserved(path) }
            .setNegativeButton(R.string.dialog_cancel) { _, _ -> /* do nothing */ }
            .create()
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CALLBACK_OPEN_DIR_SELECTOR -> {
                data?.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR)
                    ?.run(presenter::onAddObserved)
            }
        }
    }

}
