package black.bracken.picsorter.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import black.bracken.picsorter.R
import black.bracken.picsorter.util.NOTIFICATION_COLOR
import org.koin.core.KoinComponent
import org.koin.core.inject

object ObservingNotification : KoinComponent {

    const val NOTIFICATION_ID = 7609

    private const val CHANNEL_ID = "observer"
    private const val CHANNEL_NAME = "常駐用の通知(非表示にして下さい)"
    private val context by inject<Context>()

    val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_LOW
    ).apply {
        lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        setShowBadge(false)
    }

    fun get(): Notification =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .apply {
                color = NOTIFICATION_COLOR
                setColorized(true)
                setOngoing(true)
                setSmallIcon(R.drawable.app_icon)
                setContentText(context.getString(R.string.observer_text))
                setSubText(context.getString(R.string.observer_text_sub))
            }
            .build()

}