package black.bracken.picsorter.manipulating.notification

import android.app.Notification
import android.app.NotificationChannel

interface ObservingNotificationProvider {
    val notificationId: Int
    val channel: NotificationChannel

    fun create(): Notification
}