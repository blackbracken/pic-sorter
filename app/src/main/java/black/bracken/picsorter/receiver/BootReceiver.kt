package black.bracken.picsorter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.KoinComponent
import org.koin.core.inject

@ExperimentalCoroutinesApi
class BootReceiver : BroadcastReceiver(), KoinComponent {

    private val imageObserverRepository: black.bracken.picsorter.data.repository.ImageObserverRepository by inject()
    private val settingsRepository: black.bracken.picsorter.data.repository.SettingsRepository by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != BOOT_ACTION) return

        if (settingsRepository.shouldRunOnBoot) {
            imageObserverRepository.enableObserver()
        }
    }

    companion object {
        const val BOOT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

}