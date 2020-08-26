package black.bracken.picsorter.ui.settings.top

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import black.bracken.picsorter.data.repository.ImageObserverRepository
import black.bracken.picsorter.data.repository.SettingsRepository

class TopViewModel(
    private val imageObserverRepository: ImageObserverRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val enablesObserver by lazy { MutableLiveData<Boolean>(imageObserverRepository.verifyWhetherToRun()) }
    val runsOnBoot by lazy { MutableLiveData<Boolean>(settingsRepository.shouldRunOnBoot) }

    fun switchToEnableImageObserver(isEnabled: Boolean) {
        if (isEnabled) {
            imageObserverRepository.enableObserver()
        } else {
            imageObserverRepository.disableObserver()
        }
    }

    fun switchToRunOnBoot(isEnabled: Boolean) {
        settingsRepository.shouldRunOnBoot = isEnabled
    }

}