package black.bracken.picsorter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import black.bracken.picsorter.data.repository.ImageObserverRepository
import black.bracken.picsorter.data.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

@ExperimentalCoroutinesApi
class BootReceiver : BroadcastReceiver(), KoinComponent {

    private val imageObserverRepository: ImageObserverRepository by inject()
    private val settingsRepository: SettingsRepository by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != BOOT_ACTION) return

        GlobalScope.launch {
            if (settingsRepository.shouldRunOnBootFlow.first()) {
                imageObserverRepository.enableObserver()
            }
        }
    }

    companion object {
        const val BOOT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

}