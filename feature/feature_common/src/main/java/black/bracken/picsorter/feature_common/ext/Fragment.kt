package black.bracken.picsorter.feature_common.ext

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import black.bracken.picsorter.feature_common.R
import com.afollestad.materialdialogs.MaterialDialog

fun Fragment.hasExternalStoragePermission(): Boolean =
    context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED

fun Fragment.createIntentForExternalStoragePermission(onAllowed: () -> Unit): ActivityResultLauncher<String> =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isAllowed ->
        if (isAllowed) {
            onAllowed()
        } else if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MaterialDialog(requireContext()).show {
                icon(R.drawable.ic_touch_app_black)
                title(R.string.dialog_permission_request_title)
                message(R.string.dialog_permission_request_subtitle)

                positiveButton {
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:${activity?.packageName}")
                    ).apply {
                        addCategory(Intent.CATEGORY_DEFAULT)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }.let { startActivity(it) }
                }
            }
        }
    }