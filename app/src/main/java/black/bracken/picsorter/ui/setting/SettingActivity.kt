package black.bracken.picsorter.ui.setting

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import black.bracken.picsorter.DirectoryObserveService
import black.bracken.picsorter.R
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * @author BlackBracken
 */
class SettingActivity : AppCompatActivity(), SettingBehind.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)

        val channel = NotificationChannel(
            DirectoryObserveService.CHANNEL_ID_OBSERVER,
            getString(R.string.name_observe_channel),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            setShowBadge(false)
        }
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)

        toggleButtonRunning.setOnCheckedChangeListener { _, _ ->
            startForegroundService(Intent(this, DirectoryObserveService::class.java))
        }
    }

}
