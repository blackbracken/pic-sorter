package black.bracken.picsorter.manipulating.notification

import android.app.Notification
import android.app.NotificationChannel

interface DetectionNotificationProvider {
    val notificationId: Int
    val channel: NotificationChannel

    fun create(filePath: String): Notification

}