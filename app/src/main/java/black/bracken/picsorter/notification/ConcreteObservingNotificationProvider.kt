package black.bracken.picsorter.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import androidx.core.app.NotificationCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.manipulating.notification.ObservingNotificationProvider

internal class ConcreteObservingNotificationProvider(
    private val context: Context
) : ObservingNotificationProvider {
    override val notificationId = 7609

    override val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_LOW
    ).apply {
        lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        setShowBadge(false)
    }

    override fun create(): Notification {
        val actionToOpenNotificationSettings = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            .apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, "black.bracken.picsorter")
                putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
            }
            .let { intent ->
                PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            .let { pendingIntent ->
                NotificationCompat.Action(
                    R.mipmap.ic_stat_notification_icon,
                    context.getString(R.string.notification_hide_self),
                    pendingIntent
                )
            }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .apply {
                color = Color.argb(0, 80, 80, 80) // it will be replaced as constant
                setColorized(true)
                setOngoing(true)
                setSmallIcon(R.mipmap.ic_stat_notification_icon)
                setContentText(context.getString(R.string.observer_text))
                setSubText(context.getString(R.string.observer_text_sub))
                addAction(actionToOpenNotificationSettings)
            }
            .build()
    }

    companion object {
        private const val CHANNEL_ID = "observer"
        private const val CHANNEL_NAME = "常駐用の通知(非表示にして下さい)"
    }

}