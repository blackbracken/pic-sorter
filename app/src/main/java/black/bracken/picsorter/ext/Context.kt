package black.bracken.picsorter.ext

import android.app.NotificationManager
import android.content.Context

val Context.notificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager