package black.bracken.picsorter.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.presentation.settings.directorieschooser.SettingsDirectoriesChooserActivity
import black.bracken.picsorter.ui.top.TopFragment

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)

        // TODO: notice to agree permission if doesn't
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TopFragment())
                .commitNow()
        }
    }

    // TODO: remove this after all activities are replaced with fragments.
    fun openDirectoriesChooser() {
        startActivity(Intent(this, SettingsDirectoriesChooserActivity::class.java))
    }

}