package black.bracken.picsorter.settings_observed_directory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import black.bracken.picsorter.data.repository.SettingsRepository

class DirectoriesChooserViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val directoryPathObservedList: LiveData<List<String>> get() = _directoryPathObservedList
    private val _directoryPathObservedList = MutableLiveData(settingsRepository.directoryPathList)

    fun tryAddDirectory(directoryPath: String): Boolean {
        if (directoryPath in directoryPathObservedList.value ?: listOf()) return false

        settingsRepository.addDirectoryPath(directoryPath)
        _directoryPathObservedList.value = settingsRepository.directoryPathList

        return true
    }

    fun removeDirectory(directoryPath: String) {
        settingsRepository.removeDirectoryPath(directoryPath)
        _directoryPathObservedList.value = settingsRepository.directoryPathList
    }

}