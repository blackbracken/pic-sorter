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
import black.bracken.picsorter.manipulating.notification.DetectionNotificationProvider
import black.bracken.picsorter.manipulating.ui.ManipulatingFragment
import black.bracken.picsorter.ui.ManipulatingActivity

internal class ConcreteDetectionNotificationProvider(
    private val context: Context
) : DetectionNotificationProvider {

    override val notificationId = 7610

    override val channel: NotificationChannel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        setSound(null, null)
        setShowBadge(true)
    }

    override fun create(filePath: String): Notification {
        val actionToOpenManipulatingView = Intent(context, ManipulatingActivity::class.java)
            .apply {
                putExtra(ManipulatingFragment.EXTRA_IMAGE_PATH, filePath)
                putExtra(ManipulatingFragment.EXTRA_OPEN_SIMPLE_MANIPULATING, false)

                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            }
            .let { intent ->
                PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
                )
            }
            .let { pendingIntent ->
                NotificationCompat.Action(
                    R.mipmap.ic_stat_notification_icon,
                    context.getString(R.string.notification_detection_button_manipulate),
                    pendingIntent
                )
            }

        val actionToChooseSimpleManipulation =
            Intent(context, ManipulatingActivity::class.java)
                .apply {
                    putExtra(ManipulatingFragment.EXTRA_IMAGE_PATH, filePath)
                    putExtra(ManipulatingFragment.EXTRA_OPEN_SIMPLE_MANIPULATING, true)

                    flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                }
                .let { intent ->
                    PendingIntent.getActivity(
                        context,
                        1,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
                    )
                }
                .let { pendingIntent ->
                    NotificationCompat.Action(
                        R.mipmap.ic_stat_notification_icon,
                        context.getString(R.string.notification_detection_button_simple_manipulating),
                        pendingIntent
                    )
                }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .apply {
                color = Color.argb(0, 80, 80, 80) // TODO: it will be replaced as constant
                setAutoCancel(true)
                setColorized(true)
                setDefaults(0)
                setSmallIcon(R.mipmap.ic_stat_notification_icon)
                setContentText(context.getString(R.string.notification_detection_description))

                addAction(actionToOpenManipulatingView)
                addAction(actionToChooseSimpleManipulation)
            }
            .build()
            .apply {
                flags = Notification.FLAG_AUTO_CANCEL
            }
    }

    companion object {
        private const val CHANNEL_ID = "detection"
        private const val CHANNEL_NAME = "更新の検出通知"
    }

}