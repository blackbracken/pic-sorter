package black.bracken.picsorter.ui.manipulating

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import black.bracken.picsorter.service.ManipulatingTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class ManipulatingViewModel(
    val imagePath: String,
    private val context: Context
) : ViewModel() {

    val image = File(imagePath)

    val imageDirectoryPath = MutableLiveData(image.parent)
    val imageNewName = MutableLiveData<String>(image.nameWithoutExtension)
    val shouldDeleteLater = MutableLiveData<Boolean>(false)
    val secondsToDelete = MutableLiveData<Int>(null)

    val imageExtension = ".${image.extension}"

    fun manipulate() {
        val task = ManipulatingTask(image, context,
            ManipulatingTask.TaskRequest(
                imageDirectoryPath.value,
                imageNewName.value
                    ?.takeUnless { newName -> newName.isEmpty() },
                shouldDeleteLater.value
                    ?.takeIf { shouldDelete -> shouldDelete }
                    ?.let { secondsToDelete.value }
            )
        )

        // Execute in GlobalScope because the task may be executed out of ViewModelScope.
        GlobalScope.launch { task.execute() }
    }

    fun dump() {
        val task = ManipulatingTask(
            image, context,
            ManipulatingTask.TaskRequest(null, null, 0)
        )

        GlobalScope.launch { task.execute() }
    }

}
