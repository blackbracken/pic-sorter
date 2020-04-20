package black.bracken.picsorter.ui.manipulating

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.ext.notificationManager
import black.bracken.picsorter.notification.DetectionNotification
import black.bracken.picsorter.service.ManipulatingTask
import black.bracken.picsorter.service.model.SimpleManipulating
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ManipulatingViewModel(
    imagePath: String,
    private val context: Context,
    private val simpleManipulatingsDao: SimpleManipulatingsDao
) : ViewModel() {

    val image = File(imagePath)

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
                    0
                )
            ).execute()
        }
    }

    fun removeNotificationIfExists() {
        context.notificationManager.cancel(DetectionNotification.NOTIFICATION_ID)
    }

    suspend fun getAllSimpleManipulatings() = withContext(Dispatchers.IO) {
        simpleManipulatingsDao.getAll().map { it.toModel() }
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
