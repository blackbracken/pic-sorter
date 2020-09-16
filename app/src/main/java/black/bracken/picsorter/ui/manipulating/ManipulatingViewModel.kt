package black.bracken.picsorter.ui.manipulating

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import black.bracken.picsorter.data.model.SimpleManipulating
import black.bracken.picsorter.data.repository.SimpleManipulatingRepository
import black.bracken.picsorter.ext.notificationManager
import black.bracken.picsorter.notification.DetectionNotification
import black.bracken.picsorter.service.ManipulatingTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class ManipulatingViewModel(
    imagePath: String,
    private val context: Context,
    simpleManipulatingRepository: SimpleManipulatingRepository
) : ViewModel() {
    val image = File(imagePath)

    val simpleManipulatings = simpleManipulatingRepository.getSimpleManipulatingsFlow().asLiveData()

    val imageDirectoryPath = MutableLiveData(image.parent)
    val imageNewName = MutableLiveData(image.nameWithoutExtension)
    val shouldDeleteLater = MutableLiveData(false)
    val secondsToDelete = MutableLiveData<Int>(null)

    val imageExtension = ".${image.extension}"

    fun manipulate() {
        // Execute in GlobalScope because the task may be executed out of ViewModelScope.
        GlobalScope.launch {
            ManipulatingTask(
                image, context,
                ManipulatingTask.TaskRequest(
                    imageDirectoryPath.value,
                    imageNewName.value
                        ?.takeIf { it.isNotEmpty() },
                    shouldDeleteLater.value
                        ?.takeIf { shouldDelete -> shouldDelete }
                        ?.let { secondsToDelete.value }
                )
            ).execute()
        }
    }

    fun dump() {
        GlobalScope.launch {
            ManipulatingTask(
                image, context,
                ManipulatingTask.TaskRequest(
                    null,
                    null,
                    0,
                )
            ).execute()
        }
    }

    fun removeNotificationIfExists() {
        context.notificationManager.cancel(DetectionNotification.NOTIFICATION_ID)
    }

    fun scheduleTask(manipulating: SimpleManipulating) = GlobalScope.launch {
        ManipulatingTask(
            image, context,
            ManipulatingTask.TaskRequest(
                manipulating.newDirectoryPath, null, manipulating.secondsToDelete
            )
        ).execute()
    }

}
