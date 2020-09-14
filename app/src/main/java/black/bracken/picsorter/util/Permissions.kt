package black.bracken.picsorter.util

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.fragment.app.Fragment

// TODO: rename and move into better

fun Fragment.hasExternalStoragePermission(): Boolean =
    context?.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED