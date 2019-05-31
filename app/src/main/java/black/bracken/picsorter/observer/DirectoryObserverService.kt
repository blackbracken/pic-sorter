package black.bracken.picsorter.observer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import androidx.core.app.NotificationCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.ext.notificationManager

/**
 * @author BlackBracken
 */
class DirectoryObserverService : Service() {

    companion object {
        private const val CHANNEL_ID = "observer"
        private const val CHANNEL_NAME = "ObserverChannel"

        private const val NOTIFICATION_ID = 7609
        private val NOTIFICATION_COLOR = Color.argb(0, 80, 80, 80)
    }

    private val channel by lazy {
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
            .apply {
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                setShowBadge(false)
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent ?: throw IllegalStateException("unreachable because this always returns START_NOT_STICKY")

        notificationManager.createNotificationChannel(channel)

        NotificationCompat
            .Builder(this, CHANNEL_ID)
            .apply {
                color = NOTIFICATION_COLOR
                setColorized(true)
                setOngoing(true)
                setSmallIcon(R.drawable.app_icon)
                setContentText(getString(R.string.observer_text))
            }
            .build()
            .also { notification ->
                startForeground(NOTIFICATION_ID, notification)
            }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

}