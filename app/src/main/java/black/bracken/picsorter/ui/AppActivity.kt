package black.bracken.picsorter.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import black.bracken.picsorter.ui.dirchooser.DirectoriesChooserFragment
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
            TopFragment().open()
        }
    }

    fun openDirectoriesChooser() {
        DirectoriesChooserFragment().open()
    }

    private fun Fragment.open() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, this)
            .commitNow()
    }

}