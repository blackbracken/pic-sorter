package black.bracken.picsorter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import black.bracken.picsorter.presentation.observer.ObserverService
import black.bracken.picsorter.service.repository.settings.SettingsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class BootReceiver : BroadcastReceiver(), KoinComponent {

    private val settingsRepository: SettingsRepository by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != BOOT_ACTION) return

        if (settingsRepository.shouldRunOnBoot) {
            context.startForegroundService(Intent(context, ObserverService::class.java))
        }
    }

    companion object {
        const val BOOT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

}