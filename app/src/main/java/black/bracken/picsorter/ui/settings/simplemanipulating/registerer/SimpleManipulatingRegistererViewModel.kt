package black.bracken.picsorter.ui.settings.simplemanipulating.registerer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import black.bracken.picsorter.data.SimpleManipulating
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.db.entity.toEntity

class SimpleManipulatingRegistererViewModel(
    private val simpleManipulatingsDao: SimpleManipulatingsDao
) : ViewModel() {

    val manipulatingName = MutableLiveData("")
    val directoryPath = MutableLiveData<String?>(null)
    val shouldDeleteLater = MutableLiveData(false)
    val secondsToDelete = MutableLiveData<Int?>()

    suspend fun register(): VerificationResult {
        val name = manipulatingName.value
            .takeUnless { it.isNullOrBlank() }
            ?: return VerificationResult.MUST_NOT_EMPTY

        if (simpleManipulatingsDao.findManipulatingByName(name) != null) {
            return VerificationResult.ALREADY_REGISTERED_UNDER_SAME_NAME
        }

        simpleManipulatingsDao.insertManipulating(
            SimpleManipulating(
                name,
                directoryPath.value?.takeUnless { it.isBlank() },
                secondsToDelete.value
            ).toEntity()
        )

        return VerificationResult.SUCCEED
    }

    enum class VerificationResult {
        SUCCEED,
        MUST_NOT_EMPTY,
        ALREADY_REGISTERED_UNDER_SAME_NAME,
    }

}