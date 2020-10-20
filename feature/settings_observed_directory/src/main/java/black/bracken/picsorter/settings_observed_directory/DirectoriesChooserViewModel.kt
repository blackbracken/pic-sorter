package black.bracken.picsorter.settings_observed_directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.data.repository.SettingsRepository
import kotlinx.coroutines.launch

class DirectoriesChooserViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val directoryPathObservedList = settingsRepository.directoryPathListFlow.asLiveData()

    fun tryAddDirectory(directoryPath: String): Boolean {
        if (directoryPath in directoryPathObservedList.value ?: listOf()) return false

        viewModelScope.launch {
            settingsRepository.addObservedDirectoryPath(directoryPath)
        }

        return true
    }

    fun removeDirectory(directoryPath: String) {
        viewModelScope.launch {
            settingsRepository.removeObservedDirectoryPath(directoryPath)
        }
    }

}