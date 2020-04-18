package black.bracken.picsorter.ui.settings.simplemanipulating.registerer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.db.entity.toEntity
import black.bracken.picsorter.service.model.SimpleManipulating
import kotlinx.coroutines.launch

class SimpleManipulatingRegistererViewModel(
    private val simpleManipulatingsDao: SimpleManipulatingsDao
) : ViewModel() {

    val manipulatingName: LiveData<String> = MutableLiveData("")
    val directoryPath = MutableLiveData<String>("")
    val shouldDeleteLater = MutableLiveData(false)
    val secondsToDelete = MutableLiveData<Int>()

    fun register() {
        viewModelScope.launch {
            val name = manipulatingName.value ?: return@launch

            simpleManipulatingsDao.insertManipulating(
                SimpleManipulating(
                    name,
                    directoryPath.value?.takeIf { it.isNotBlank() },
                    secondsToDelete.value
                ).toEntity()
            )
        }
    }

}