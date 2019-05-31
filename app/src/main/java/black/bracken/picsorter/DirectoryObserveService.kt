package black.bracken.picsorter

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import androidx.core.app.NotificationCompat

/**
 * @author BlackBracken
 */
class DirectoryObserveService : Service() {

    companion object {
        const val CHANNEL_ID_OBSERVER = "observer"

        private val NOTIFICATION_COLOR = Color.argb(0, 80, 80, 80)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat
            .Builder(this, CHANNEL_ID_OBSERVER)
            .apply {
                color = NOTIFICATION_COLOR
                setColorized(true)
                setOngoing(true)
                setSmallIcon(R.drawable.app_icon)
                setContentText(getString(R.string.notification_message_observing))
            }
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

}