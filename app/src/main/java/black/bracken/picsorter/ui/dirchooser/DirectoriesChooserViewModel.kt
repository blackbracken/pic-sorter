package black.bracken.picsorter.ui.dirchooser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import black.bracken.picsorter.service.repository.settings.SettingsRepository

class DirectoriesChooserViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val directoryPathObservedList: LiveData<List<String>> get() = _directoryPathObservedList
    private val _directoryPathObservedList =
        MutableLiveData<List<String>>(settingsRepository.directoryPathList)

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