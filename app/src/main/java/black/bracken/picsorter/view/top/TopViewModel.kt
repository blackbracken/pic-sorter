package black.bracken.picsorter.view.top

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import black.bracken.picsorter.presentation.observer.ObserverService
import black.bracken.picsorter.repository.settings.SettingsRepository

class TopViewModel(
    private val settingsRepository: SettingsRepository,
    application: Application
) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    val enablesObserverLiveData by lazy {
        val enablesNow = tryToDisableObserver()
        if (enablesNow) enableObserver()

        MutableLiveData<Boolean>(enablesNow)
    }
    val runOnBootLiveData by lazy { MutableLiveData<Boolean>(settingsRepository.shouldRunOnBoot) }

    fun enableObserver() {
        context.startForegroundService(Intent(context, ObserverService::class.java))
    }

    fun tryToDisableObserver(): Boolean {
        return context.stopService(Intent(context, ObserverService::class.java))
    }

    fun switchToRunOnBoot(isEnabled: Boolean) {
        settingsRepository.shouldRunOnBoot = isEnabled
    }

}