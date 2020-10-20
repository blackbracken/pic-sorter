package black.bracken.picsorter.settings_top.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.data.repository.ImageObserverRepository
import black.bracken.picsorter.data.repository.SettingsRepository
import kotlinx.coroutines.launch

class TopViewModel(
    private val imageObserverRepository: ImageObserverRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val enablesObserver by lazy { MutableLiveData(imageObserverRepository.verifyWhetherToRun()) }
    val runsOnBoot = settingsRepository.shouldRunOnBootFlow.asLiveData()

    fun switchToEnableImageObserver(isEnabled: Boolean) {
        if (isEnabled) {
            imageObserverRepository.enableObserver()
        } else {
            imageObserverRepository.disableObserver()
        }
    }

    fun toggleWhetherRunOnBoot(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.toggleWhetherRunOnBoot(isEnabled)
        }
    }

}