package black.bracken.picsorter.ui.settings.simplemanipulating.top

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.service.model.SimpleManipulating
import black.bracken.picsorter.service.repository.SimpleManipulatingRepository
import kotlinx.coroutines.launch

class SimpleManipulatingTopViewModel(
    private val manipulatingRepository: SimpleManipulatingRepository
) : ViewModel() {

    val manipulatingList = MutableLiveData<List<SimpleManipulating>>(listOf())

    init {
        viewModelScope.launch {
            manipulatingList.value = manipulatingRepository.loadAll()
        }
    }

    fun removeManipulating(manipulating: SimpleManipulating) {
        manipulatingList.value = manipulatingList.value?.minus(manipulating) ?: listOf()

        viewModelScope.launch {
            manipulatingRepository.removeByName(manipulating.name)
        }
    }

}