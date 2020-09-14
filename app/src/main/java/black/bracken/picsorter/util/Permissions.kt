package black.bracken.picsorter.util

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import com.afollestad.materialdialogs.MaterialDialog

// TODO: rename and move into better

fun Fragment.hasExternalStoragePermission(): Boolean =
    context?.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED

fun openDialogForExternalStoragePermission(fragment: Fragment) {
    MaterialDialog(fragment.requireContext()).show {
        icon(R.drawable.ic_touch_app_black)
        title(R.string.dialog_permission_request_title)
        message(R.string.dialog_permission_request_subtitle)

        positiveButton {
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${fragment.activity?.packageName}")
            ).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }.let { fragment.startActivity(it) }
        }
    }
}