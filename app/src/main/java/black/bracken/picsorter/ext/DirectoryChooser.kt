package black.bracken.picsorter.ext

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import net.rdrei.android.dirchooser.DirectoryChooserActivity
import net.rdrei.android.dirchooser.DirectoryChooserConfig

fun FragmentActivity.startDirectoryChooserActivity(requestCode: Int) {
    val intent = Intent(this, DirectoryChooserActivity::class.java)
        .apply { setExtraForDirectoryChooser() }

    startActivityForResult(intent, requestCode)
}

private fun Intent.setExtraForDirectoryChooser() {
    putExtra(
        DirectoryChooserActivity.EXTRA_CONFIG,
        DirectoryChooserConfig.builder()
            .newDirectoryName("NewDirectory")
            .allowReadOnlyDirectory(false)
            .allowNewDirectoryNameModification(false)
            .build()
    )
}