package black.bracken.picsorter.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        toggleButtonRunning.setOnCheckedChangeListener { _, isChecked -> presenter.onToggleObserverService(isChecked) }
    }

    override fun enableObserverButton() {
        toggleButtonRunning.isChecked = true
    }

    override fun disableObserverButton() {
        toggleButtonRunning.isChecked = false
    }

}
