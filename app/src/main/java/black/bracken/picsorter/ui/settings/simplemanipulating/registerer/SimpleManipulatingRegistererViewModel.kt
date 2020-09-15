package black.bracken.picsorter.ui.settings.simplemanipulating.registerer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.data.SimpleManipulating
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.db.entity.toEntity
import kotlinx.coroutines.launch

class SimpleManipulatingRegistererViewModel(
    private val simpleManipulatingsDao: SimpleManipulatingsDao
) : ViewModel() {
    private val _verificationResult = MutableLiveData<VerificationResult>()
    val verificationResult: LiveData<VerificationResult> get() = _verificationResult

    val manipulatingName = MutableLiveData("")
    val directoryPath = MutableLiveData<String?>(null)
    val shouldDeleteLater = MutableLiveData(false)
    val secondsToDelete = MutableLiveData<Int?>()

    fun register() = viewModelScope.launch {
        suspend fun verifyInput(): VerificationResult {
            val name = manipulatingName.value
                .takeUnless { it.isNullOrBlank() }
                ?: return VerificationResult.MustNotEmpty

            if (simpleManipulatingsDao.findManipulatingByName(name) != null) {
                return VerificationResult.AlreadyRegisteredUnderSameName
            }

            return VerificationResult.Succeed(
                SimpleManipulating(
                    name,
                    directoryPath.value?.takeUnless { it.isBlank() },
                    secondsToDelete.value
                )
            )
        }

        val verificationResult = verifyInput()
        if (verificationResult is VerificationResult.Succeed) {
            simpleManipulatingsDao.insertManipulating(verificationResult.simpleManipulating.toEntity())
        }

        _verificationResult.postValue(verificationResult)
    }

    sealed class VerificationResult {
        class Succeed(val simpleManipulating: SimpleManipulating) : VerificationResult()
        object MustNotEmpty : VerificationResult()
        object AlreadyRegisteredUnderSameName : VerificationResult()
    }

}