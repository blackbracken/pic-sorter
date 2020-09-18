package black.bracken.picsorter.settings_top.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import black.bracken.picsorter.data.repository.ImageObserverRepository
import black.bracken.picsorter.data.repository.SettingsRepository

class TopViewModel(
    private val imageObserverRepository: ImageObserverRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val enablesObserver by lazy { MutableLiveData(imageObserverRepository.verifyWhetherToRun()) }
    val runsOnBoot by lazy { MutableLiveData(settingsRepository.shouldRunOnBoot) }

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