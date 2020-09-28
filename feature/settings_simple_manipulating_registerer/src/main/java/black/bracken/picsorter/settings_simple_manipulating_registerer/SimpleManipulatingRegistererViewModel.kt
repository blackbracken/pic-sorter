package black.bracken.picsorter.settings_simple_manipulating_registerer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import black.bracken.picsorter.data.model.SimpleManipulating
import black.bracken.picsorter.data.repository.SimpleManipulatingRepository
import kotlinx.coroutines.launch

class SimpleManipulatingRegistererViewModel(
    private val simpleManipulatingRepository: SimpleManipulatingRepository
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

            if (simpleManipulatingRepository.findByName(name) != null) {
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
            simpleManipulatingRepository.add(verificationResult.simpleManipulating)
        }

        _verificationResult.postValue(verificationResult)
    }

    sealed class VerificationResult {
        data class Succeed(val simpleManipulating: SimpleManipulating) : VerificationResult()
        object MustNotEmpty : VerificationResult()
        object AlreadyRegisteredUnderSameName : VerificationResult()
    }

}