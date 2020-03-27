package black.bracken.picsorter.view.top

import androidx.lifecycle.ViewModel
import black.bracken.picsorter.repository.settings.SettingsRepository

class TopViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    fun switchToEnableObserver(isEnabled: Boolean) {
        // TODO not implemented yet
    }

    fun switchToRunOnBoot(isEnabled: Boolean) {
        settingsRepository.shouldRunOnBoot = isEnabled
    }

}