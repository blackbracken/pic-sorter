package black.bracken.picsorter.ui.simplemanipulatingsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.service.repository.SimpleManipulatingRepository
import kotlinx.coroutines.launch

class SimpleManipulatingSettingsViewModel(
    private val manipulatingRepository: SimpleManipulatingRepository
) : ViewModel() {

    val manipulatingNameList: LiveData<List<String>> get() = _manipulatingNameList
    private val _manipulatingNameList = MutableLiveData<List<String>>(listOf())

    init {
        viewModelScope.launch {
            _manipulatingNameList.value = manipulatingRepository.loadAllNames()
        }
    }

    // TODO implement

}