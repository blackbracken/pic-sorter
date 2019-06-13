package black.bracken.picsorter.presentation.observer

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
import black.bracken.picsorter.repository.setting.SettingRepository

/**
 * @author BlackBracken
 */
class ObserverService : Service(), ObserverContract.View {

    companion object {
        private const val STATIONED_CHANNEL_ID = "observer"
        private const val STATIONED_CHANNEL_NAME = "ObserverChannel"

        private const val DETECTION_CHANNEL_ID = "detection"
        private const val DETECTION_CHANNEL_NAME = "DetectionChannel"

        private const val STATIONED_NOTIFICATION_ID = 7609
        private const val DETECTION_NOTIFICATION_ID = 7610

        private val NOTIFICATION_COLOR = Color.argb(0, 80, 80, 80)
    }

    override val presenter by lazy { ObserverPresenter(this, this) }

    private val stationedChannel =
        NotificationChannel(STATIONED_CHANNEL_ID, STATIONED_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW).apply {
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            setShowBadge(false)
        }

    private val detectionChannel =
        NotificationChannel(DETECTION_CHANNEL_ID, DETECTION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            setShowBadge(false)
        }

    private val observer by lazy {
        DirectoriesObserver(
            SettingRepository(this).observedDirectoryPathList,
            presenter::onClickToOpenManipulator
        )
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        notificationManager.createNotificationChannels(listOf(stationedChannel, detectionChannel))
        presenter.onStart()
        observer.startWatching()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
        observer.stopWatching()
    }

    override fun stationNotification() {
        NotificationCompat
            .Builder(this, STATIONED_CHANNEL_ID)
            .apply {
                color = NOTIFICATION_COLOR
                setColorized(true)
                setOngoing(true)
                setSmallIcon(R.drawable.app_icon)
                setContentText(getString(R.string.observer_text))
            }
            .build()
            .also { notification -> startForeground(STATIONED_NOTIFICATION_ID, notification) }
    }

    override fun clearNotification() {
        notificationManager.cancel(STATIONED_NOTIFICATION_ID)
    }

    override fun showDetectionHeadsUpNotification() {
        NotificationCompat
            .Builder(this, DETECTION_CHANNEL_ID)
            .apply {
                color = NOTIFICATION_COLOR
                setColorized(true)
                setDefaults(0)
                setSmallIcon(R.drawable.app_icon)
                setContentText("Detect.") // TODO: write on strings.xml
            }
            .build()
            .also { notification -> notificationManager.notify(DETECTION_NOTIFICATION_ID, notification) }
    }

}