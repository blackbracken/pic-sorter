package black.bracken.picsorter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import black.bracken.picsorter.presentation.observer.ObserverService
import black.bracken.picsorter.repository.setting.SettingRepository

/**
 * @author BlackBracken
 */
class BootReceiver : BroadcastReceiver() {

    companion object {
        const val BOOT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != BOOT_ACTION) return

        val settingRepository = SettingRepository(context)

        if (settingRepository.shouldRunOnBoot) {
            context.startForegroundService(Intent(context, ObserverService::class.java))
        }
    }

}