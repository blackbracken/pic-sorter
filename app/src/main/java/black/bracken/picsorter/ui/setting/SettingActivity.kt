package black.bracken.picsorter.ui.setting

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import black.bracken.picsorter.R
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * @author BlackBracken
 */
class SettingActivity : AppCompatActivity(), SettingBehind.View {

    override val presenter by lazy { SettingPresenter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)

        presenter.start()

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

        switchEnables.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleObserverService(isChecked) }
        switchRunOnBoot.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleRunOnBoot(isChecked) }
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

}
