package black.bracken.picsorter.ui.setting

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.model.ObservedDirectoryPath
import kotlinx.android.synthetic.main.activity_setting.*
import net.rdrei.android.dirchooser.DirectoryChooserActivity
import net.rdrei.android.dirchooser.DirectoryChooserConfig

/**
 * @author BlackBracken
 */
class SettingActivity : AppCompatActivity(), SettingBehind.View {

    companion object {
        private const val CALLBACK_OPEN_SELECTOR = 1945
    }

    override val presenter: SettingBehind.Presenter by lazy { SettingPresenter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)

        // TODO: notice to agree permission
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

        switchEnables.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleObserverService(isChecked) }
        switchRunOnBoot.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleRunOnBoot(isChecked) }
        buttonObservedAdd.setOnClickListener { openObservedPathSelector() }

        presenter.start()
    }

    override fun switchOnToEnableObserver() {
        switchEnables.isChecked = true
    }

    override fun switchOffToEnableObserver() {
        switchEnables.isChecked = false
    }

    override fun switchOnToRunOnBoot() {
        switchRunOnBoot.isChecked = true
    }

    override fun switchOffToRunOnBoot() {
        switchRunOnBoot.isChecked = false
    }

    override fun openObservedPathSelector() {
        val intent = Intent(this, DirectoryChooserActivity::class.java)
            .apply {
                putExtra(
                    DirectoryChooserActivity.EXTRA_CONFIG,
                    DirectoryChooserConfig.builder()
                        .newDirectoryName("")
                        .allowReadOnlyDirectory(false)
                        .allowNewDirectoryNameModification(false)
                        .build()
                )
            }

        startActivityForResult(intent, CALLBACK_OPEN_SELECTOR)
    }

    override fun addObservedPath(path: ObservedDirectoryPath) = TODO("not implemented yet")

    override fun removeObservedPath(path: ObservedDirectoryPath) = TODO("not implemented yet")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CALLBACK_OPEN_SELECTOR -> {
                val selectedPath = data?.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR)

                if (selectedPath != null) {
                    presenter.onSucceedToAddObservedDirectoryPath(ObservedDirectoryPath(selectedPath))
                } else {
                    presenter.onFailToAddObservedDirectoryPath()
                }
            }
        }
    }

}
