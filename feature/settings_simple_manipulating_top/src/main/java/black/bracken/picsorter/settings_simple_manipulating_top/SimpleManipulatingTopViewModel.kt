package black.bracken.picsorter.settings_simple_manipulating_top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.data.repository.SimpleManipulatingRepository
import black.bracken.picsorter.model.SimpleManipulating
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