package black.bracken.picsorter.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.receiver.ChooseSimpleManipulatingReceiver
import black.bracken.picsorter.ui.manipulating.ManipulatingActivity
import black.bracken.picsorter.util.NOTIFICATION_COLOR
import org.koin.core.KoinComponent
import org.koin.core.inject

object DetectionNotification : KoinComponent {

    const val NOTIFICATION_ID = 7610

    private const val CHANNEL_ID = "detection"
    private const val CHANNEL_NAME = "更新の検出通知"
    private const val CALLBACK_OPEN_MANIPULATOR = 2145
    private val context by inject<Context>()

    val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        setSound(null, null)
        setShowBadge(true)
    }

    fun get(filePath: String): Notification {
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

        val actionToChooseSimpleManipulation =
            Intent(context, ChooseSimpleManipulatingReceiver::class.java)
                .apply {
                    putExtra(ChooseSimpleManipulatingReceiver.EXTRA_IMAGE_PATH, filePath)
                }
                .let { intent ->
                    PendingIntent.getBroadcast(context, 0, intent, 0)
                }
                .let { pendingIntent ->
                    NotificationCompat.Action(
                        R.drawable.app_icon,
                        "OPEN_SIMPLE", // TODO replace
                        pendingIntent
                    )
                }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .apply {
                color = NOTIFICATION_COLOR
                setAutoCancel(true)
                setColorized(true)
                setDefaults(0)
                setSmallIcon(R.drawable.app_icon)
                setContentText(context.getString(R.string.notification_detection_description))
                addAction(actionToOpenManipulatingView)
                addAction(actionToChooseSimpleManipulation)
            }
            .build()
            .apply {
                flags = Notification.FLAG_AUTO_CANCEL
            }
    }

}