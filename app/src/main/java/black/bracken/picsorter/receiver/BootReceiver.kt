package black.bracken.picsorter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import black.bracken.picsorter.presentation.observer.ObserverService
import black.bracken.picsorter.repository.settings.SettingsRepository

/**
 * @author BlackBracken
 */
class BootReceiver : BroadcastReceiver() {

    companion object {
        const val BOOT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != BOOT_ACTION) return

        val settingsRepository = SettingsRepository(context)

        if (settingsRepository.shouldRunOnBoot) {
            context.startForegroundService(Intent(context, ObserverService::class.java))
        }
    }

}