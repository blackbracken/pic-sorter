package black.bracken.picsorter.presentation.settings.top

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.presentation.settings.directorieschooser.SettingsDirectoriesChooserActivity
import kotlinx.android.synthetic.main.activity_settings_top.*
import kotlinx.android.synthetic.main.dialog_copyright.view.*

/**
 * @author BlackBracken
 */
class SettingsTopActivity
    : AppCompatActivity(), SettingsTopContract.View {

    companion object {
        private const val LINK_COPYRIGHT = "file:///android_asset/copyright.html"
    }

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

        // TODO: notice to agree permission if doesn't
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        switchEnable.setOnCheckedChangeListener { _, isChecked ->
            presenter.onToggleObserverService(
                isChecked
            )
        }
        switchRunOnBoot.setOnCheckedChangeListener { _, isChecked ->
            presenter.onToggleRunOnBoot(
                isChecked
            )
        }
        textDirectories.setOnClickListener { presenter.onOpenDirectoriesChooser() }
        imageDirectoriesArrow.setOnClickListener { presenter.onOpenDirectoriesChooser() }

        presenter.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_settings_copyright -> presenter.onShowCopyright()
        }

        return super.onOptionsItemSelected(item)
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

    override fun showCopyright() {
        val webView = View
            .inflate(this, R.layout.dialog_copyright, findViewById<ViewGroup>(R.id.layoutCopyright))
            .also { view ->
                view.webCopyright.loadUrl(LINK_COPYRIGHT)
            }

        AlertDialog.Builder(this)
            .setTitle(R.string.title_copyright)
            .setPositiveButton(R.string.dialog_ok, null)
            .setView(webView)
            .show()
    }

}
