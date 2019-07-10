package black.bracken.picsorter.presentation.observer

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import androidx.core.app.NotificationCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.ext.notificationManager
import black.bracken.picsorter.presentation.manipulating.ManipulatingActivity
import black.bracken.picsorter.repository.setting.SettingRepository

/**
 * @author BlackBracken
 */
class ObserverService : Service(), ObserverContract.View {

    companion object {
        const val CALLBACK_OPEN_MANIPULATOR = 2145

        const val STATIONED_NOTIFICATION_ID = 7609
        const val DETECTION_NOTIFICATION_ID = 7610

        private const val STATIONED_CHANNEL_ID = "observer"
        private const val STATIONED_CHANNEL_NAME = "ObserverChannel"

        private const val DETECTION_CHANNEL_ID = "detection"
        private const val DETECTION_CHANNEL_NAME = "DetectionChannel"

        private val NOTIFICATION_COLOR = Color.argb(0, 80, 80, 80)
    }

    override val presenter by lazy { ObserverPresenter(this) }

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
            presenter::onDetectAdditionInObserved
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

    override fun stationNotificationForForeground() {
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

    override fun clearNotificationForForeground() {
        notificationManager.cancel(STATIONED_NOTIFICATION_ID)
    }

    override fun showDetectionHeadsUp(imagePath: String) {
        val actionToManipulate = Intent(this, ManipulatingActivity::class.java)
            .apply {
                putExtra(ManipulatingActivity.EXTRA_IMAGE_PATH, imagePath)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            }
            .let { intent ->
                PendingIntent.getActivity(
                    applicationContext,
                    CALLBACK_OPEN_MANIPULATOR,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
                )
            }
            .let { pendingIntent ->
                NotificationCompat.Action(
                    R.drawable.app_icon,
                    getString(R.string.notification_detection_button_manipulate),
                    pendingIntent
                )
            }

        NotificationCompat
            .Builder(this, DETECTION_CHANNEL_ID)
            .apply {
                color = NOTIFICATION_COLOR
                setColorized(true)
                setDefaults(0)
                setSmallIcon(R.drawable.app_icon)
                setContentText(getString(R.string.notification_detection_description))
                addAction(actionToManipulate)
            }
            .build()
            .apply {
                flags = Notification.FLAG_AUTO_CANCEL
            }
            .also { notification -> notificationManager.notify(DETECTION_NOTIFICATION_ID, notification) }
    }

}