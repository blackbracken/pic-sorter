package black.bracken.picsorter.presentation.settings.top

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.presentation.settings.directorieschooser.SettingsDirectoriesChooserActivity
import kotlinx.android.synthetic.main.activity_settings_top.*

/**
 * @author BlackBracken
 */
class SettingsTopActivity : AppCompatActivity(),
    SettingsTopContract.View {

    override val presenter: SettingsTopContract.Presenter by lazy {
        SettingsTopPresenter(
            this,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_top)
        setSupportActionBar(toolbarSettings)

        // TODO: notice to agree permission if not
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

        switchEnable.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleObserverService(isChecked) }
        switchRunOnBoot.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleRunOnBoot(isChecked) }
        textDirectories.setOnClickListener { presenter.onOpenDirectoriesChooser() }
        imageDirectoriesArrow.setOnClickListener { presenter.onOpenDirectoriesChooser() }

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

    override fun openDirectoriesChooser() {
        startActivity(Intent(this, SettingsDirectoriesChooserActivity::class.java))
    }

}
