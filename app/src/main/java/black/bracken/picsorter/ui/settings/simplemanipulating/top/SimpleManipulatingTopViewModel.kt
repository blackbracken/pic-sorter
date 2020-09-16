package black.bracken.picsorter.ui.settings.simplemanipulating.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.data.SimpleManipulating
import black.bracken.picsorter.data.repository.SimpleManipulatingRepository
import kotlinx.coroutines.launch

class SimpleManipulatingTopViewModel(
    private val manipulatingRepository: SimpleManipulatingRepository
) : ViewModel() {

    val manipulatingList = manipulatingRepository.getSimpleManipulatingsFlow().asLiveData()

    fun removeManipulating(manipulating: SimpleManipulating) {
        viewModelScope.launch {
            manipulatingRepository.removeByName(manipulating.name)
        }
    }

}