package black.bracken.picsorter.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.ext.notificationManager
import black.bracken.picsorter.ui.manipulating.ManipulatingActivity
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetectionNotification(private val filePath: String) : KoinComponent {

    private val context by inject<Context>()

    fun show() {
        val notificationManager = context.notificationManager

        notificationManager.cancel(NOTIFICATION_ID)

        val actionToOpenManipulatingView = Intent(context, ManipulatingActivity::class.java)
            .apply {
                putExtra(ManipulatingActivity.EXTRA_IMAGE_PATH, filePath)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            }
            .let { intent ->
                PendingIntent.getActivity(
                    context,
                    CALLBACK_OPEN_MANIPULATOR,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
                )
            }
            .let { pendingIntent ->
                NotificationCompat.Action(
                    R.drawable.app_icon,
                    context.getString(R.string.notification_detection_button_manipulate),
                    pendingIntent
                )
            }

        NotificationCompat.Builder(context, CHANNEL_ID)
            .apply {
                color = NOTIFICATION_COLOR
                setColorized(true)
                setDefaults(0)
                setSmallIcon(R.drawable.app_icon)
                setContentText(context.getString(R.string.notification_detection_description))
                addAction(actionToOpenManipulatingView)
            }
            .build()
            .apply {
                flags = Notification.FLAG_AUTO_CANCEL
            }
            .also { notification ->
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
    }

    companion object {
        const val NOTIFICATION_ID = 7610
        const val CALLBACK_OPEN_MANIPULATOR = 2145

        private const val CHANNEL_ID = "detection"
        private const val CHANNEL_NAME = "更新の検出通知"
        private val NOTIFICATION_COLOR = Color.argb(0, 80, 80, 80)

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            setSound(null, null)
            setShowBadge(true)
        }
    }

}